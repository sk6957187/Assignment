import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav style={{ background: "#eee", padding: "10px" }}>
      <Link to="/dashboard" style={{ border: "1px solid black", padding: "5px", borderRadius: "5px" }}>🏠Home</Link>
    </nav>
  );
}

export default Navbar;
