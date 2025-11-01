import React, { useContext } from 'react';
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
import {ToastContainer} from "react-toastify";
import MyOrders from './pages/myOrders/MyOrders';
import { StoreContext } from './context/StoreContext';

const HomeComponent = () => {
    const {token} = useContext(StoreContext);
    return (
        <>
            <Menubar />
            <ToastContainer />
            <div className="container mt-3">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/explore" element={<ExploreFood />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route path="/food/:id" element={<FoodDetails />} />
                    <Route path="/cart" element={token ? <Cart /> : <Login/>} />
                    <Route path='/order' element={token ? <PlaceOrder /> : <Login/>} />
                    <Route path="/login" element={token ? <Home /> : <Login/>} />
                    <Route path="/register" element={token ? <Home/> : <Register />} />
                    <Route path="/myorders" element={token ? <MyOrders/> : <Login/>} />
                </Routes>
            </div>
        </>
    );
};

export default HomeComponent;
