import React, { useContext, useState } from 'react';
import { StoreContext } from '../../context/StoreContext';

const FoodList = ({ category, searchText }) => {
  const { foodList } = useContext(StoreContext);
  const [selectedFood, setSelectedFood] = useState(null);


  const filteredFood = foodList.filter(food => (
    (category === 'All' || food.category === category) && food.name.toLowerCase().includes(searchText.toLowerCase())
  ));


  const handleImageClick = (food) => {
    setSelectedFood(food);
  };

  const handleClose = () => {
    setSelectedFood(null);
  };

  return (
    <div className="container mt-4">
      <div className="row">
        {filteredFood.length > 0 ? (
          filteredFood.map((food, index) => (
            <div
              key={index}
              className="col-12 col-sm-6 col-md-4 col-lg-3 d-flex justify-content-center mb-4"
            >
              <div className="card" style={{ maxWidth: '320px' }}>
                <button
                  onClick={() => handleImageClick(food)}
                  style={{ borderRadius: '10px', border: 'solid 1px red' }}
                >
                  <img
                    src={food.imageUrl}
                    className="card-img-top"
                    alt={food.name}
                    height={250}
                    width={60}
                  />
                </button>
                <div className="card-body">
                  <h5 className="card-title">{food.name}</h5>
                  <p className="card-text">{food.description}</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <span className="h5 mb-0">&#8377;{food.price}.00</span>
                    <div>
                      <i className="bi bi-star-fill text-warning"></i>
                      <i className="bi bi-star-fill text-warning"></i>
                      <i className="bi bi-star-fill text-warning"></i>
                      <i className="bi bi-star-fill text-warning"></i>
                      <i className="bi bi-star-half text-warning"></i>
                      <small className="text-muted">(4.5)</small>
                    </div>
                  </div>
                </div>
                <div className="card-footer d-flex justify-content-between bg-light">
                  <button className="btn btn-primary btn-sm">Add to Cart</button>
                  <button className="btn btn-outline-secondary btn-sm">
                    <i className="bi bi-heart"></i>
                  </button>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="col-12 text-center">
            <h2>No Food Items Available</h2>
          </div>
        )}
      </div>

      {/* Modal for food details */}
      {selectedFood && (
        <div
          className="modal show fade d-block"
          tabIndex="-1"
          style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">{selectedFood.name}</h5>
                <button type="button" className="btn-close" onClick={handleClose}></button>
              </div>
              <div className="modal-body text-center">
                <img
                  src={selectedFood.imageUrl}
                  alt={selectedFood.name}
                  className="img-fluid mb-3"
                  style={{ maxHeight: '300px' }}
                />
                <p>{selectedFood.description}</p>
                <p>
                  <strong>Category:</strong> {selectedFood.category}
                </p>
                <p>
                  <strong>Price:</strong> ₹{selectedFood.price}.00
                </p>
              </div>
              <div className="modal-footer">
                <button className="btn btn-secondary" onClick={handleClose}>
                  Close
                </button>
                <button className="btn btn-primary">Add to Cart</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default FoodList;
