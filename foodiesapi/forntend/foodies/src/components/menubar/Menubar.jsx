import React, { useContext } from 'react';
import { assets } from '../../assets/assets.js';
import './Menubar.css';
import { Link } from 'react-router-dom';
import { StoreContext } from '../../context/StoreContext.jsx';

const Menubar = () => {
    const { quantities } = useContext(StoreContext);
    const totalItems = Object.values(quantities).reduce((acc, curr) => acc + curr, 0);
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
            <div className="container">
                <Link to={"/"}><img src={assets.logo} alt="logo" className="logo navbar-brand mx-4" height={45} width={45} /></Link>

                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    {/* Left Links */}
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <Link className="nav-link" to="/">Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/explore">Explore</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/contact">Contact Us</Link>
                        </li>
                    </ul>

                    {/* Right Section */}
                    <div className="d-flex align-items-center menubar-right gap-4">
                        <Link to={"/cart"} >
                            <div className="position-relative me-3">
                                <img src={assets.cart} alt="cart" className="cart-icon" height={48} width={48} />
                                <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                    {totalItems}
                                </span>
                            </div>
                        </Link>
                        <button className="btn btn-outline-primary me-2">
                            <i className="bi bi-person-circle me-1"></i> Login
                        </button>
                        <button className="btn btn-outline-success">
                            <i className="bi bi-box-arrow-right me-1"></i> Register
                        </button>
                    </div>
                </div>
            </div>
        </nav>
    );
};

export default Menubar;
