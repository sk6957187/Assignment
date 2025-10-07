import React, { useState } from 'react';
import { toast } from "react-toastify";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import "./Register.css";
import { register, registerUser } from '../../service/Authservice';

const Register = () => {
    var navigate = useNavigate();
    const [data, setData] = useState({ name: '', email: '', password: '' });

    const onChangeHandler = (e) => {
        const { name, value } = e.target;
        setData(prev => ({ ...prev, [name]: value }));
    };

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        var responseData = null;

        try {
            var response = await registerUser(data);

            if (response.status === 201) {
                toast.success("Registration completed, Please login.");
                navigate("/login");
            } else {
                toast.error("Unable to register, Please try again.");
            }
        } catch (error) {
            const responseData = error.response?.data;   // may be undefined if no server reply

            toast.error(
                responseData?.message
                    ? responseData.message
                    : "Unable to register, try again."
            );

            console.error("Error message:", error.message);
            if (error.response) {
                console.error("Server responded with:", error.response.status, error.response.data);
            }
        }

    };


    return (
        <>
            <div className="container register-container">
                <div className="row">
                    <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                        <div className="card border-0 shadow rounded-3 my-5">
                            <div className="card-body p-4 p-sm-5 register-form">
                                <h5 className="card-title text-center mb-5 fw-light fs-5">Register</h5>
                                <form onSubmit={onSubmitHandler}>
                                    <div className="form-floating mb-3">
                                        <input type="text" className="form-control" id="floatingName" placeholder="ABC KUMAR" name="name" onChange={onChangeHandler}
                                            value={data.name} required />
                                        <label htmlFor="floatingName">FullName</label>
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="email" className="form-control" id="floatingInput" placeholder="name@example.com" name="email" onChange={onChangeHandler}
                                            value={data.email} required />
                                        <label htmlFor="floatingInput">Email address</label>
                                    </div>
                                    <div className="form-floating mb-3">
                                        <input type="password" className="form-control" id="floatingPassword" placeholder="Password" name="password" onChange={onChangeHandler}
                                            value={data.password} required />
                                        <label htmlFor="floatingPassword">Password</label>
                                    </div>
                                    <div className="d-grid">
                                        <button className="btn btn-outline-primary btn-login text-uppercase " type="submit">Sign
                                            Up</button>
                                    </div>
                                    <div className="btn btn-outline-danger btn-login text-uppercase d-grid mt-2">
                                        Reset
                                    </div>
                                    <div className='mt-4'>
                                        Already have an account? <a href="/login" className='text-decoration-none'>Log In</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Register
