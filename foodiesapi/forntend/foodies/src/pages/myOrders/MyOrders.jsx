import React, { useState, useContext, useEffect } from 'react';
import { StoreContext } from '../../context/StoreContext';
import axios from "axios";
import { assets } from '../../assets/assets';

const MyOrders = () => {
    const { token } = useContext(StoreContext);
    const [data, setData] = useState([]);

    const fetchOrder = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/orders", {
                headers: { Authorization: `Bearer ${token}` },
            });
            console.log(response.data);
            setData(response.data);
        } catch (error) {
            console.error("Error fetching orders:", error);
        }
    };

    useEffect(() => {
        if (token) fetchOrder();
    }, [token, fetchOrder]);

    return (
        <div className="container">
            <div className="py-5 row justify-content-center">
                <div className="col-11 card p-3">
                    <h4 className="mb-3">My Orders</h4>
                    <table className="table table-responsive">
                        <tbody>
                            {data && data.length > 0 ? (
                                data.map((order, index) => (
                                    <tr key={index}>
                                        <td>
                                            <img src={assets.delivery} alt="food" height={48} width={40} />
                                        </td>
                                        <td>
                                            {order.orderItemList && order.orderItemList.map((item, i) => (
                                                <span key={i}>
                                                    {item.productName} × {item.quantity}
                                                    {i < order.orderItemList.length - 1 ? ', ' : ''}
                                                </span>
                                            ))}
                                        </td>
                                        <td>₹ {order.amount.toFixed(2)}</td>
                                        <td>Items: {order.orderItemList ? order.orderItemList.length : 0}</td>
                                        <td className="fw-bold text-capitalize">
                                            ● {order.status}
                                        </td>
                                        <td>
                                            <button
                                                className="btn btn-sm btn-warning"
                                                onClick={fetchOrder}
                                            >
                                                <i className="bi bi-arrow-clockwise"></i>
                                            </button>
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

export default MyOrders;
