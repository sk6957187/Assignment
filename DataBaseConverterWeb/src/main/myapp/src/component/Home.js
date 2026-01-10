
import { useNavigate } from "react-router-dom";

function Home(){
    const navigate = useNavigate();

    return (
    <>
        <h2>Home page</h2>
        <button onClick={() => navigate("/converter")}>Data Base Converter</button>
    </>
    );
}


export default Home;