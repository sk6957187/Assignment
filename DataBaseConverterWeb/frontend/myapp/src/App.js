import './App.css';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import Home from './component/Home';
import Converter from './component/databaseConverter/Converter';
import ReadExcel from './component/databaseConverter/ReadExcel';
import Navbar from './component/Navbar';
import ReadSQL from './component/databaseConverter/ReadSQL';

function AppWrapper() {
  const location = useLocation();
  const isHomePage = location.pathname === "/dashboard";
  const isApp = location.pathname === "/";

  return (
    <>
      <div className="App">
        <h1 style={{ color: "red", fontSize: "32px", fontFamily: "cursive" }}>
          <u><i>Super App</i></u>
        </h1>
        <hr />
      </div>

      {!isApp && !isHomePage && <Navbar />}

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/dashboard" element={<Home />} />
        <Route path="/converter" element={<Converter />}>
          <Route path="excel" element={<ReadExcel />} />
          <Route path="sql" element={<ReadSQL />} />
        </Route>
      </Routes>
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
