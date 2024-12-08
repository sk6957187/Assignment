import DailyReport from './document/DailyReport'; // Correct import with PascalCase
import {BrowserRouter} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <DailyReport /> {/* Correct usage with PascalCase */}
      </BrowserRouter>
    </div>
  );
}

export default App;

