import { useEffect } from 'react';
import WeatherMonitor from './component/WeatherMonitor';
import ReactGA from "react-ga4";


function App() {

  useEffect(() => {
    const GA_MEASUREMENT_ID = process.env.REACT_APP_GA_MEASUREMENT_ID;
    if (GA_MEASUREMENT_ID) {
      ReactGA.initialize(GA_MEASUREMENT_ID);
      ReactGA.send({
        hitType: "pageview",
        page: window.location.pathname,
        title: "App.jsx",
      });
    } else {
      console.warn("Google Analytics ID not found in environment variables");
    }
  }, []);
  return (
    <div className="App">
      <div className = "container">
        <WeatherMonitor />
      </div>
    </div>
  );
}

export default App;
