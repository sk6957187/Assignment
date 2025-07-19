import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Menubar from './components/menubar/Menubar';
import Home from './pages/home/Home';
import ExploreFood from './pages/exploreFood/ExploreFood';
import Contact from './pages/contact/Contact';
import FoodDetails from './pages/foodDetails/FoodDetails';
import Cart from './pages/cart/Cart';

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
                </Routes>
            </div>
        </>
    );
};

export default HomeComponent;
