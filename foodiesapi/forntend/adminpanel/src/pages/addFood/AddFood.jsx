import React, { useState, useEffect } from 'react';
import { assets } from '../../assets/assets';
import { toast } from 'react-toastify';
import { addFood } from '../../components/services/FoodServices';

const AddFood = () => {
  const [image, setImage] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);

  const [data, setData] = useState({
    name: '',
    description: '',
    price: '',
    category: '',
  });

  const onChangeHandler = (event) => {
    const { name, value } = event.target;
    setData((prev) => ({ ...prev, [name]: value }));
  };

  const onImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setImage(file);
      setImagePreview(URL.createObjectURL(file));
    }
  };

  const onSubmitHandler = async (event) => {
    event.preventDefault();

    if (!image) {
      toast.error("Please select an image.");
      return;
    }

    try {
      await addFood(data, image);
      toast.success("Food added successfully");

      // Reset form
      setData({ name: "", description: "", category: "", price: "" });
      setImage(null);
      setImagePreview(null);
    } catch (error) {
      console.error("Error adding food:", error.response || error.message);
      toast.error("Error adding food...");
    }
  };

  // Clean up image preview URL to prevent memory leaks
  useEffect(() => {
    return () => {
      if (imagePreview) URL.revokeObjectURL(imagePreview);
    };
  }, [imagePreview]);

  return (
    <div className="container mt-2">
      <div className="row justify-content-center">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-4">Add Food</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3 text-center">
                <label htmlFor="image" className="form-label">
                  <img
                    src={imagePreview || assets.upload}
                    alt="Upload"
                    width={98}
                    style={{ cursor: 'pointer', objectFit: 'cover' }}
                  />
                </label>
                <input
                  type="file"
                  id="image"
                  name="image"
                  accept="image/*"
                  hidden
                  onChange={onImageChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name</label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  name="name"
                  required
                  onChange={onChangeHandler}
                  value={data.name}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea
                  className="form-control"
                  placeholder="Write description here..."
                  id="description"
                  name="description"
                  rows="5"
                  required
                  onChange={onChangeHandler}
                  value={data.description}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="category" className="form-label">Category</label>
                <select
                  name="category"
                  id="category"
                  className="form-control"
                  required
                  onChange={onChangeHandler}
                  value={data.category}
                >
                  <option value="">Select Category</option>
                  <option value="biryani">Biryani</option>
                  <option value="pizza">Pizza</option>
                  <option value="cake">Cake</option>
                  <option value="rolls">Rolls</option>
                  <option value="salad">Salad</option>
                  <option value="ice cream">Ice Cream</option>
                  <option value="burger">Burger</option>
                </select>
              </div>

              <div className="mb-3">
                <label htmlFor="price" className="form-label">Price</label>
                <input
                  type="number"
                  placeholder="₹100"
                  name="price"
                  id="price"
                  className="form-control"
                  required
                  onChange={onChangeHandler}
                  value={data.price}
                />
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
