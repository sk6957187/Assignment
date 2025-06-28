import './App.css';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import Home from './component/Home';
import Converter from './component/databaseConverter/Converter';
import Navbar from './component/Navbar';

function AppWrapper() {
  const location = useLocation();
  const isHomePage = location.pathname === "/dashboard";
  const isApp = location.pathname === "/";

  return (
    <>
      <div className="App">
        <h1 style={{ color: "blue", fontSize: "32px", fontFamily: "cursive"}}>
          <u><i>My APP</i></u>
        </h1>
      </div>
      <div>
        {!isApp && !isHomePage && <Navbar />}
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/dashboard" element={<Home />} />
          <Route path="/converter" element={<Converter />} />
        </Routes>
      </div>
    </>
  );
}

function App() {
  return (
    <BrowserRouter>
      <AppWrapper />
    </BrowserRouter>
  );
}

export default App;
