import React, { useState } from 'react';
import { assets } from '../../assets/assets';
import axios from 'axios';
import { toast } from 'react-toastify';
import { addFood } from '../../components/services/FoodServices';


const AddFood = () => {
  const [image, setImage] = useState(null);
  const[data, setData] = useState({
    name:'',
    description:"",
    price:"",
    category:""

  });

  const onChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setData(data => ({...data, [name]: value}));
  }
  
  const onSubmitHandler = async (event) => {
    event.preventDefault();
    if(!image){
      toast.error("Please select an image.");
      return;
    }
    
    try {
      await addFood(data, image);
      toast.success("Food added successfully");
      setData({name: "", description: "", category: "", price:""});
      setImage(null);

      // if(response.status === 200){
      //   alert("Food added successfully");
      //   setData({name: "", description: "", category: "", price:""});
      //   setImage(null);
      // }
    } catch (error) {
      console.log(error.response || error.message);
      toast.error("Error adding food...");
    }
  };

  return (
    <div className="container mt-2">
      <div className="row justify-content-center">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-4">Add Food</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3 text-center">
                <label htmlFor="image" className="form-label">
                  <img src={image ? URL.createObjectURL(image) : assets.upload} alt="Upload" width={98} style={{ cursor: 'pointer' }} />
                </label>
                <input type="file" className="form-control" id="image" name="image" hidden onChange={(e) => setImage(e.target.files[0])} />
              </div>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control" id="name" name="name" required onChange={onChangeHandler} value={data.name} />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea className="form-control" placeholder='Write description here...' id="description" name="description" rows="5" required onChange={onChangeHandler} value={data.description}></textarea>
              </div>

              <div className="mb-3">
                <label htmlFor="category" className="form-label">Category</label>
                <select name="category" id="category" className="form-control" required onChange={onChangeHandler} value={data.category}>
                  <option value="">Select Category</option>
                  <option value="biryani">Biryani</option>
                  <option value="pizza">Pizza</option>
                  <option value="cake">Cake</option>
                  <option value="rolls">Rolls</option>
                  <option value="salad">Salad</option>
                  <option value="ice Cream">Ice Cream</option>
                </select>
              </div>

              <div className="mb-3">
                <label htmlFor="price" className="form-label">Price</label>
                <input type="number" placeholder='₹100' name="price" id="price" className="form-control" required onChange={onChangeHandler} value={data.price}/>
              </div>

              <button type="submit" className="btn btn-primary w-100">Save</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddFood;
