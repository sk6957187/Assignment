import React, { useContext } from 'react';
import './cart.css';
import { StoreContext } from '../../context/StoreContext';
import { Link, useNavigate } from 'react-router-dom';
import { calculateCartTotals } from '../../util/cartUtils';

const Cart = () => {
  const { foodList, increaseQty, decreaseQty, quantities, clearAll } = useContext(StoreContext);

  const navigate = useNavigate();
  const cartItems = foodList.filter((food) => quantities[food.id] > 0);
  const {subTotal, shipping, tax, total} = calculateCartTotals(cartItems, quantities)

  const removeAll = (foodId) => {
    clearAll(foodId);
  };

  return (
    <div className="container py-5">
      <h1 className="mb-5">Your Shopping Cart</h1>
      <div className="row">
        <div className="col-lg-8">
          {cartItems.length === 0 ? (
            <p>Your cart is empty.</p>
          ) : (
            <div className="card mb-4">
              <div className="card-body">
                {cartItems.map((item) => (
                  <div className="row cart-item mb-3" key={item.id}>
                    <div className="col-md-3">
                      <img src={item.imageUrl} alt={item.name} className="img-fluid rounded" />
                    </div>
                    <div className="col-md-5">
                      <h5 className="card-title">{item.name}</h5>
                      <p className="text-muted">Category: {item.category}</p>
                    </div>
                    <div className="col-md-2">
                      <div className="input-group">
                        <button
                          className="btn btn-outline-secondary btn-sm"
                          type="button"
                          onClick={() => decreaseQty(item.id)}
                        >
                          -
                        </button>
                        <input
                          style={{ maxWidth: "100px" }}
                          type="text"
                          className="form-control form-control-sm text-center quantity-input"
                          value={quantities[item.id]}
                          readOnly
                        />
                        <button
                          className="btn btn-outline-secondary btn-sm"
                          type="button"
                          onClick={() => increaseQty(item.id)}
                        >
                          +
                        </button>
                      </div>
                    </div>
                    <div className="col-md-2 text-end">
                      <p className="fw-bold">&#8377; {(item.price * quantities[item.id]).toFixed(2)}</p>
                      <button className="btn btn-sm btn-outline-danger" onClick={() => removeAll(item.id)}>
                        <i className="bi bi-trash"></i>
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
          <div className="text-start mb-4">
            <Link to={"/"}>
              <button className="btn btn-outline-primary">
              <i className="bi bi-arrow-left me-2"></i>Continue Shopping
            </button>
            </Link>
          </div>
        </div>

        

        <div className="col-lg-4">
          <div className="card mt-4 mb-4">
            <div className="card-body">
              <h5 className="card-title mb-3">Apply Promo Code</h5>
              <div className="input-group mb-3">
                <input type="text" className="form-control" placeholder="Enter promo code" />
                <button className="btn btn-outline-secondary" type="button">Apply</button>
              </div>
            </div>
          </div>
          <div className="card cart-summary">
            <div className="card-body">
              <h5 className="card-title mb-4">Order Summary</h5>
              <div className="d-flex justify-content-between mb-3">
                <span>Subtotal</span>
                <span>&#8377; {subTotal.toFixed(2)}</span>
              </div>
              <div className="d-flex justify-content-between mb-3">
                <span>Shipping</span>
                <span>₹ {shipping.toFixed(2)}</span>
              </div>
              <div className="d-flex justify-content-between mb-3">
                <span>Tax (18%)</span>
                <span>₹ {tax.toFixed(2)}</span>
              </div>
              <hr />
              <div className="d-flex justify-content-between mb-4">
                <strong>Total</strong>
                <strong>₹ {total.toFixed(2)}</strong>
              </div>
              <button className="btn btn-primary w-100" disabled = {cartItems.length === 0} onClick={() => navigate("/order")}>Proceed to Checkout</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
 