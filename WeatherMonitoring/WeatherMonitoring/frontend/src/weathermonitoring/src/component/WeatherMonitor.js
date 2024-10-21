import React, { Component } from 'react';
import axios from 'axios';

class WeatherMonitor extends Component {
    constructor(props) {
        super(props);
        this.state = {
            city: 'DELHI',
            weather: null,
            summary: [],
            threshold: 35,
            alertMessage: '',
        };
    }

    fetchCurrentWeather = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/weather/current/${this.state.city}`);
            this.setState({ weather: response.data });
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching current weather:', error);
        }
    };

    fetchDailySummary = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/weather/daily-summary/${this.state.city}`);
            this.setState({ summary: response.data });
            console.log(response.data);
        } catch (error) {
            console.error('Error fetching daily summary:', error);
        }
    };

    setTemperatureAlert = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/api/weather/set-alert/${this.state.city}`, {
                threshold: Number(this.state.threshold),
            });
            this.setState({ alertMessage: response.data });
            console.log(`resp {} : ${response.data}`);
        } catch (error) {
            console.error('Error setting alert:', error);
        }
    };

    componentDidMount() {
        this.fetchCurrentWeather();
        this.fetchDailySummary();
        this.interval = setInterval(this.fetchCurrentWeather, 300000); // 300000 ms = 5 minutes
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    render() {
        const { city, weather, summary, threshold, alertMessage } = this.state;

        return (
            <div>
                <h1>Weather Monitoring</h1>
                <div>
                    <label>Select City: </label>
                    <select value={city} onChange={(e) => this.setState({ city: e.target.value.toUpperCase() })}>
                        <option value="DELHI">Delhi</option>
                        <option value="MUMBAI">Mumbai</option>
                        <option value="CHENNAI">Chennai</option>
                        <option value="BANGALORE">Bangalore</option>
                        <option value="KOLKATA">Kolkata</option>
                        <option value="HYDERABAD">Hyderabad</option>
                    </select>
                </div>

                {weather && (
                    <div className="row">
                        <h2>Current Weather in {city}</h2>
                        <p>Temperature: {weather.temp.toFixed(2)}°C</p>
                        <p>Feels Like: {weather.feelsLike.toFixed(2)}°C</p>
                        <p>Condition: {weather.condition}</p>
                        <p>Wind Speed: {weather.windSpeed} km/s</p>
                        <p>Humidity: {weather.humidity}%</p>
                        <p>Sea Level: {weather.seaLevel} hPa</p>
                        <p>Visibility: {(weather.visibility / 1000).toFixed(2)} km</p>
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
                    <div>
                        <input
                            type="number"
                            value={threshold}
                            onChange={(e) => this.setState({ threshold: e.target.value })}
                        />
                        <button type="button" className="btn btn-primary" onClick={this.setTemperatureAlert}>
                            Set Alert
                        </button>
                    </div>
                    {alertMessage && <p className="badge bg-danger">{alertMessage}</p>}
                </div>
            </div>
        );
    }
}

export default WeatherMonitor;
