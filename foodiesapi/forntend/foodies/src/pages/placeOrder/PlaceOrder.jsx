import React, { useContext, useState } from "react";
import "./PlaceOrder.css";
import { toast } from "react-toastify";
import { assets } from "../../assets/assets";
import { StoreContext } from "../../context/StoreContext";
import { calculateCartTotals } from "../../util/cartUtils";
import { RAZORPAY_KEY } from "../../util/contants";
import axios from "axios";
import { useNavigate } from "react-router-dom";


const PlaceOrder = () => {
  const { foodList, quantities, setQuantities, token } = useContext(StoreContext);
  const navigate = useNavigate();

  const [data, setData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    landMark: "",
    address: "",
    city: "",
    state: "",
    country: "",
    zip: ""
  });

  const onChangeHandler = (event) => {
    const { name, value } = event.target;
    setData((prev) => ({ ...prev, [name]: value }));
  };

  const cartItems = foodList.filter((food) => quantities[food.id] > 0);
  const { subTotal, shipping, tax, total } = calculateCartTotals(cartItems, quantities);

  const onSubmitHandler = async (event) => {
    event.preventDefault();

    const orderData = {
      userAddress: `${data.firstName} ${data.lastName}, ${data.address}, ${data.city}, ${data.state}, ${data.country}, ${data.zip}`,
      phoneNumber: data.phoneNumber,
      email: data.email,
      orderItemList: cartItems.map((item) => ({
        productId: item.id,
        quantity: quantities[item.id],
        price: item.price * quantities[item.id],
        category: item.category,
        imageUrl: item.imageUrl,
        description: item.description,
        productName: item.name
      })),
      amount: total.toFixed(2),
      orderStatus: "Preparing"
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/orders/create",
        orderData, {headers: {Authorization: `Bearer ${token}`}}
      );

      if (response.status === 201 && response.data.razorpayOrderId) {
        initiateRazorpayPayment(response.data);
      } else {
        toast.error("Unable to place order. Please try again!");
      }
    } catch (error) {
      console.error(error); 
      toast.error("Unable to place order. Please try again..!!");
    }
  };

  const initiateRazorpayPayment = (order) => {
    const options = {
      key: RAZORPAY_KEY,
      amount: order.amount * 100,
      currency: "INR",
      name: "Food Land",
      description: "Food order payment",
      order_id: order.razorpayOrderId,
      handler: async function (razorpayResponse) {
        await verifyPayment(razorpayResponse);
      },
      prefill: {
        name: `${data.firstName} ${data.lastName}`,
        email: data.email,
        contact: data.phoneNumber
      },
      theme: { color: "#3399cc" },
      modal: {
        ondismiss: async function () {
          toast.error("Payment cancelled");
          await deleteOrder(order.id);
        }
      }
    };

    const razorpay = new window.Razorpay(options);
    razorpay.open();
  };

  const verifyPayment = async (razorpayResponse) => {
    const paymentData = {
      razorpay_payment_id: razorpayResponse.razorpay_payment_id,
      razorpay_order_id: razorpayResponse.razorpay_order_id,
      razorpay_signature: razorpayResponse.razorpay_signature
    };

    try {
      const response = await axios.post(
        "http://localhost:8080/api/orders/verify",
        paymentData,{headers: {Authorization: `Bearer ${token}`}}
      );

      if (response.status === 200) {
        toast.success("Payment successful");
        await clearCart();
        navigate("/myorders");
      } else {
        toast.error("Payment verification failed");
        navigate("/");
      }
    } catch (error) {
      console.error(error);
      toast.error("Payment verification failed");
      navigate("/");
    }
  };

  const deleteOrder = async (orderId) => {
    try {
      await axios.delete(`http://localhost:8080/api/orders/${orderId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
    } catch (error) {
      console.error(error);
      toast.error("Something went wrong. Contact support.");
    }
  };

  const clearCart = async () => {
    try {
      await axios.delete("http://localhost:8080/api/cart", {
        headers: { Authorization: `Bearer ${token}` }
      });
      setQuantities({});
    } catch (error) {
      console.error(error);
      toast.error("Error while clearing the cart.");
    }
  };

  return (
    <div className="container mt-2">
      <main>
        <div className="py-5 text-center">
          <img
            className="d-block mx-auto"
            src={assets.logo}
            alt="Logo"
            width={70}
            height={70}
            style={{ borderRadius: "13%" }}
          />
          <h2>Checkout</h2>
        </div>

        <div className="row g-5">
          {/* Cart Summary */}
          <div className="col-md-5 col-lg-4 order-md-last">
            <h4 className="d-flex justify-content-between align-items-center mb-3">
              <span className="text-primary">Your cart</span>
              <span className="badge bg-primary rounded-pill">{cartItems.length}</span>
            </h4>

            <ul className="list-group mb-3">
              {cartItems.map((item) => (
                <li key={item.id} className="list-group-item d-flex justify-content-between lh-sm">
                  <div>
                    <h6 className="my-0">{item.name}</h6>
                    <small className="text-body-secondary">
                      Qty: {quantities[item.id]}
                    </small>
                  </div>
                  <span className="text-body-secondary">
                    ₹ {(item.price * quantities[item.id]).toFixed(2)}
                  </span>
                </li>
              ))}

              <li className="list-group-item d-flex justify-content-between">
                <span>Subtotal</span>
                <strong>₹ {subTotal.toFixed(2)}</strong>
              </li>
              <li className="list-group-item d-flex justify-content-between">
                <span>Shipping</span>
                <strong>₹ {shipping.toFixed(2)}</strong>
              </li>
              <li className="list-group-item d-flex justify-content-between">
                <span>Tax (18%)</span>
                <strong>₹ {tax.toFixed(2)}</strong>
              </li>
              <li className="list-group-item d-flex justify-content-between">
                <span>Total</span>
                <strong>₹ {total.toFixed(2)}</strong>
              </li>
            </ul>
          </div>

          {/* Billing Form */}
          <div className="col-md-7 col-lg-8">
            <h4 className="mb-3">Billing address</h4>
            <form className="needs-validation" onSubmit={onSubmitHandler}>
              <div className="row g-3">
                <div className="col-sm-6">
                  <label htmlFor="firstName" className="form-label">
                    First name
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="firstName"
                    name="firstName"
                    value={data.firstName}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
                <div className="col-sm-6">
                  <label htmlFor="lastName" className="form-label">
                    Last name
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="lastName"
                    name="lastName"
                    value={data.lastName}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
                <div className="col-12">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    name="email"
                    value={data.email}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
                <div className="col-12">
                  <label htmlFor="phoneNumber" className="form-label">Phone</label>
                  <input
                    type="number"
                    className="form-control"
                    id="phoneNumber"
                    name="phoneNumber"
                    value={data.phoneNumber}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
                <div className="col-12">
                  <label htmlFor="address" className="form-label">Address</label>
                  <input
                    type="text"
                    className="form-control"
                    id="address"
                    name="address"
                    value={data.address}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
                <div className="col-12">
                  <label htmlFor="landMark" className="form-label">Landmark</label>
                  <input
                    type="text"
                    className="form-control"
                    id="landMark"
                    name="landMark"
                    value={data.landMark}
                    onChange={onChangeHandler}
                  />
                </div>

                <div className="col-md-5">
                  <label htmlFor="city" className="form-label">City</label>
                  <input
                    type="text"
                    className="form-control"
                    id="city"
                    name="city"
                    value={data.city}
                    onChange={onChangeHandler}
                    required
                  />
                </div>

                <div className="col-md-4">
                  <label htmlFor="state" className="form-label">State</label>
                  <select
                    className="form-select"
                    id="state"
                    name="state"
                    value={data.state}
                    onChange={onChangeHandler}
                    required
                  >
                    <option value="">Choose...</option>
                    <option>Bihar</option>
                    <option>Delhi</option>
                  </select>
                </div>

                <div className="col-md-5">
                  <label htmlFor="country" className="form-label">Country</label>
                  <select
                    className="form-select"
                    id="country"
                    name="country"
                    value={data.country}
                    onChange={onChangeHandler}
                    required
                  >
                    <option value="">Choose...</option>
                    <option>India</option>
                  </select>
                </div>

                <div className="col-md-3">
                  <label htmlFor="zip" className="form-label">Zip</label>
                  <input
                    type="number"
                    className="form-control"
                    id="zip"
                    name="zip"
                    value={data.zip}
                    onChange={onChangeHandler}
                    required
                  />
                </div>
              </div>

              <hr className="my-4" />
              <button
                className="w-100 btn btn-primary btn-lg"
                type="submit"
                disabled={cartItems.length === 0}
              >
                Continue to checkout
              </button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default PlaceOrder;
