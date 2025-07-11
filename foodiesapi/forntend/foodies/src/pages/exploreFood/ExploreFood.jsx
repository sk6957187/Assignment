import React, { useState } from 'react';
import FoodDisplay from '../../components/foodDisplay/FoodDisplay';
const ExploreFood = () => {
  const[category, setCategory] = useState("All");
  const[searchText, setSearchText] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Form submitted");
  };

  return (
    <>
    <div className="container py-4">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <form onSubmit={handleSubmit} className="d-flex flex-wrap gap-2">
            <div className="input-group mb-3">
              <select className="form-select" style={{ maxWidth: '150px' }} onChange={(e) => setCategory(e.target.value)}>
                <option value="All">All</option>
                <option value="pizza">Pizza</option>
                <option value="burger">Burger</option>
                <option value="biryani">Biryani</option>
                <option value="cake">Cake</option>
                <option value="ice cream">Ice Cream</option>
                <option value="rolls">Rolls</option>
                <option value="salad">Salad</option>
              </select>
              <input
                type="text"
                className="form-control flex-grow-1"
                placeholder="Search Food"
                onChange={(e) => setSearchText(e.target.value)} value={searchText}
              />
              <button className="btn btn-primary" type="submit">
                <i className="bi bi-search"></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <FoodDisplay category={category} searchText={searchText} />
    </>
  );
};

export default ExploreFood;
