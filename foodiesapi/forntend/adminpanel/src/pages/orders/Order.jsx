import React, { useState, useEffect } from 'react';
import axios from "axios";
import { assets } from '../../assets/assets';

const Order = () => {
  const [data, setData] = useState([]);

  const fetchOrders = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/orders/all");
      console.log("vbnjk");
      console.log(response.data);
      setData(response.data);
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  };

  const updateStatus = async (event, orderId) => {
    try {
      const response = await axios.patch(
        `http://localhost:8080/api/orders/status/${orderId}?status=${event.target.value}`
      );
      if (response.status === 200) {
        await fetchOrders();
      }
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="container">
      <div className="py-5 row justify-content-center">
        <div className="col-11 card p-3">
          <h4 className="mb-3">All Orders</h4>
          <table className="table table-responsive">
            <tbody>
              {data && data.length > 0 ? (
                data.map((order, index) => (
                  <tr key={index}>
                    <td>
                      <img src={assets.parcel} alt="food" height={60} width={50} />
                    </td>
                    <td>
                      <div>
                        {order.orderItemList && order.orderItemList.map((item, i) => (
                          <span key={i}>
                            {item.productName} × {item.quantity}
                            {i < order.orderItemList.length - 1 ? ', ' : ''}
                          </span>
                        ))}
                      </div>
                      <hr></hr>
                      <div>{order.userAddress}</div>
                    </td>
                    <td>₹ {order.amount.toFixed(2)}</td>
                    <td>Items: {order.orderItemList ? order.orderItemList.length : 0}</td>
                    <td>
                      <select
                        className="form-control"
                        onChange={(event) => updateStatus(event, order.orderId)}
                        value={order.status}
                      >
                        <option value="CREATED">Created</option>
                        <option value="FOOD PREPARING">Food Preparing</option>
                        <option value="OUT OF DELIVERY">Out of delivery</option>
                        <option value="DELIVERY">Delivered</option>
                      </select>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" className="text-center text-muted">
                    No orders found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Order;
