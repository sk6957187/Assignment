import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import './ListFood.css';
import { getFoodList, removeFood, updateFood } from '../../components/services/FoodServices';

const ListFood = () => {

  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [editFoodId, setEditFoodId] = useState(null);
  const [data, setData] = useState({
    id: '',
    name: '',
    description: '',
    price: '',
    category: ''
  });
  const [image, setImage] = useState(null);

  const fetchFoodList = async () => {
    setLoading(true);
    try {
      const data = await getFoodList();   // <== This calls your service method
      setList(data);
    } catch (error) {
      console.error("Error fetching food list:", error);
      toast.error("Error fetching food list. Please try again later.");
      setList([]);
    } finally {
      setLoading(false);
    }
  };


  useEffect(() => {
    fetchFoodList();
  }, []);


  const handleRemoveFood = async (foodId) => {
    if (window.confirm("Are you sure you want to delete this food item?")) {
      try {
        const response = await removeFood(foodId);
        if (response) {
          toast.success("Food item deleted successfully");
          setList(list.filter(food => food.id !== foodId));
          // fetchFoodList();
        }
      } catch (error) {
        console.error("Error deleting food item:", error);
        toast.error("Error deleting food item. Please try again later.");
      }
    }
  };

  const onChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setData(data => ({ ...data, [name]: value }));
  };


  const handleEditFood = (food) => {
    setEditMode(true);
    setEditFoodId(food.id);
    setData({
      id: food.id,
      name: food.name,
      description: food.description,
      price: food.price,
      category: food.category,
    });
    setImage(null);
  };

  const onSubmitHandler = async (event) => {
    event.preventDefault();
    if (!image) {
      toast.error("Please select an image.");
      return;
    }
    const formData = new FormData();
    formData.append("food", JSON.stringify(data));
    formData.append("file", image);
    try {

      const response = await updateFood(editFoodId, formData);
      if (response.status === 200) {
        toast.success("Food item updated successfully");
        setEditMode(false);
        setEditFoodId(null);
        setData({ name: '', description: '', price: '', category: '' });
        setImage(null);
        fetchFoodList();  // Refresh the food list after update
      }
    } catch (error) {
      console.error("Error updating food item:", error);
      toast.error("Error updating food item. Please try again later.");
    }
  };



  const renderEditForm = () => (
    <div className="container mt-2">
      <div className="row justify-content-center">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-4">Edit Food Item</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3 text-center">
                <label htmlFor="image" className="form-label">
                  <img
                    src={image ? URL.createObjectURL(image) : list.find(f => f.id === editFoodId)?.imageUrl}
                    alt="Upload"
                    width={98}
                    style={{ cursor: 'pointer' }}
                  />
                </label>
                <input
                  type="file"
                  className="form-control"
                  id="image"
                  name="image"
                  hidden
                  onChange={(e) => setImage(e.target.files[0])}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control" id="name" name="name" required onChange={onChangeHandler} value={data.name} />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea className="form-control" id="description" name="description" rows="5" required onChange={onChangeHandler} value={data.description}></textarea>
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
                <input type="number" name="price" id="price" className="form-control" required onChange={onChangeHandler} value={data.price} />
              </div>

              <button type="submit" className="btn btn-primary w-100">Update</button>
              <button type="button" className="btn btn-secondary w-100" onClick={() => setEditMode(false)}>Cancel</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );



  return (
    <div>
      {editMode ? renderEditForm() : (
        loading ? (
          <p>Loading...</p>
        ) : (
          <div className="py-5 justify-content-center d-flex col-11 card">
            <table className="table table-striped">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Price</th>
                  <th scope="col">Category</th>
                  <th scope="col">Image</th>
                  <th scope="col">Actions</th>
                </tr>
              </thead>
              <tbody>
                {list.length > 0 ? (
                  list.map((food, index) => (
                    <tr key={food.id}>
                      <th scope="row">{index + 1}</th>
                      <td>{food.name}</td>
                      <td>{food.description}</td>
                      <td>&#8377;{food.price}</td>
                      <td>{food.category}</td>
                      <td><img src={food.imageUrl} alt={food.name} width="50" /></td>
                      <td>
                        <button className="btn btn-primary btn-sm me-2" onClick={() => handleEditFood(food)}>Edit</button>
                        <button className="btn btn-danger btn-sm" onClick={() => handleRemoveFood(food.id)}>Delete</button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="text-center">No food items found.</td>
                  </tr>
                )}
              </tbody>
            </table>

          </div>
        ))}
    </div>
  )
}

export default ListFood;
