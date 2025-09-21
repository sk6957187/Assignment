import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Menubar from './components/menubar/Menubar';
import Home from './pages/home/Home';
import ExploreFood from './pages/exploreFood/ExploreFood';
import Contact from './pages/contact/Contact';
import FoodDetails from './pages/foodDetails/FoodDetails';
import Cart from './pages/cart/Cart';
import PlaceOrder from './pages/placeOrder/PlaceOrder';
import Login from './pages/login/Login';
import Register from './pages/register/Register';

const HomeComponent = () => {
    return (
        <>
            <Menubar />
            <div className="container mt-3">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/explore" element={<ExploreFood />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route path="/food/:id" element={<FoodDetails />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path='/order' element={<PlaceOrder />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                </Routes>
            </div>
        </>
    );
};

export default HomeComponent;
