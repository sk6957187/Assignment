
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const WeatherMonitor = () => {
    const [city, setCity] = useState('DELHI');
    const [weather, setWeather] = useState(null);
    const [summary, setSummary] = useState([]);
    const [threshold, setThreshold] = useState(35);
    const [alertMessage, setAlertMessage] = useState('');

   
    const fetchCurrentWeather = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/weather/current/${city}`);
            setWeather(response.data);
            //console.log(response.data);
        } catch (error) {
            console.error('Error fetching current weather:', error);
        }
    };


    const fetchDailySummary = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/weather/daily-summary/${city}`);
            setSummary(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching daily summary:', error);
        }
    };

    const setTemperatureAlert = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/api/weather/set-alert/${city}`, null, {
                params: { thresholdTemp: threshold },
            });
            setAlertMessage(response.data);
            console.log(response.data);
        } catch (error) {
            console.error('Error setting alert:', error);
        }
    };

    useEffect(() => {
        fetchCurrentWeather();
        fetchDailySummary();

       
        const interval = setInterval(fetchCurrentWeather, 300000); // 300000 ms = 5 minutes

        // Clear the interval when the component unmounts or city changes
        return () => clearInterval(interval);
    }, [city]);

    return (
        <div>
            <h1>Weather Monitoring</h1>
            <div>
                <label>Select City: </label>
                <select value={city} onChange={(e) => setCity(e.target.value.toUpperCase())}>
                    <option value="DELHI">Delhi</option>
                    <option value="MUMBAI">Mumbai</option>
                    <option value="CHENNAI">Chennai</option>
                    <option value="BANGALORE">Bangalore</option>
                    <option value="KOLKATA">Kolkata</option>
                    <option value="HYDERABAD">Hyderabad</option>
                </select>
            </div>

            {weather && (
                <div>
                    <h2>Current Weather in {city}</h2>
                    <p>Temperature: {weather.temp}°C</p>
                    <p>Feels Like: {weather.feelsLike}°C</p>
                    <p>Condition: {weather.condition}</p>
                </div>
            )}

            <div>
                <h2>Daily Summary for {city}</h2>
                <ul>
                    {summary.map((day, index) => (
                        <li key={index}>
                            Avg Temp: {day[0]}°C, Max Temp: {day[1]}°C, Min Temp: {day[2]}°C, Condition: {day[3]}
                        </li>
                    ))}
                </ul>
            </div>

            <div>
                <h3>Set Temperature Alert</h3>
                <input
                    type="number"
                    value={threshold}
                    onChange={(e) => setThreshold(e.target.value)}
                />
                <button onClick={setTemperatureAlert}>Set Alert</button>
                {alertMessage && <p>{alertMessage}</p>}
            </div>
        </div>
    );
};

export default WeatherMonitor;
