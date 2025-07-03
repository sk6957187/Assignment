import { useState } from "react";
import MenuBar from "./components/menuBar/MenuBar";
import SideBar from "./components/sideBar/SideBar";
import AddFood from "./pages/addFood/AddFood";
import ListFood from "./pages/listFood/ListFood";
import Order from "./pages/orders/Order";
import {Routes, Route} from "react-router-dom";
import{ToastContainer} from 'react-toastify';

function App() {
  const [sidebarVisible, setSidebarVisible] = useState(true);
  const toggleSidebar = () => {
    setSidebarVisible (!sidebarVisible);
  }

  return (
    <>
    <div className="d-flex" id="wrapper">
            <SideBar sidebarVisible = {sidebarVisible}/>
            <div id="page-content-wrapper">
                <MenuBar toggleSidebar = {toggleSidebar} />
                <ToastContainer />
                <div className="container-fluid">
                    <Routes>
                      <Route path = '/add' element = {<AddFood />}></Route>
                      <Route path = '/list' element = {<ListFood />}></Route>
                      <Route path = '/orders' element = {<Order />}></Route>
                      <Route path = '/' element = {<ListFood />}></Route>
                    </Routes>
                </div>
            </div>
        </div>
    </>
  );
}

export default App;
