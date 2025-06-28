import React, { useState } from 'react';
import { assets } from '../../assets/assets';

const AddFood = () => {
  const [image, setImage] = useState(false);
  const[data, setData] = useState({
    name:'',
    description:"",
    price:"",
    category:""

  });
  

  return (
    <div className="container mt-2">
      <div className="row justify-content-center">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-4">Add Food</h2>
            <form>
              <div className="mb-3 text-center">
                <label htmlFor="image" className="form-label">
                  <img src={image ? URL.createObjectURL(image) : assets.upload} alt="Upload" width={98} style={{ cursor: 'pointer' }} />
                </label>
                <input type="file" className="form-control" id="image" name="image" required hidden onChange={(e) => setImage(e.target.files[0])} />
              </div>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control" id="name" name="name" required />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea className="form-control" id="description" name="description" rows="5" required></textarea>
              </div>

              <div className="mb-3">
                <label htmlFor="category" className="form-label">Category</label>
                <select name="category" id="category" className="form-control" required>
                  <option value="">Select Category</option>
                  <option value="biryani">Biryani</option>
                  <option value="pizza">Pizza</option>
                  <option value="cake">Cake</option>
                  <option value="rolls">Rolls</option>
                  <option value="salad">Salad</option>
                  <option value="icecream">Ice Cream</option>
                </select>
              </div>

              <div className="mb-3">
                <label htmlFor="price" className="form-label">Price</label>
                <input type="number" name="price" id="price" className="form-control" required />
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
