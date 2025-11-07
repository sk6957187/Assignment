import React, { Component } from 'react';
import axios from 'axios';
import ReactGA from "react-ga4";

class WeatherMonitor extends Component {
    constructor(props) {
        super(props);
        this.state = {
            city: "DELHI",
            weather: null,
            summary: [],
            threshold: 35,
            alertMessage: '',
            alertMessageClass: '',
            baseApi: '',
        };
        this.baseApi = window.Global.baseApi;
        this.dataFetchStatus = "No";
    }

    componentDidMount() {
        if (this.dataFetchStatus === "No") {
            this.dataFetchStatus = "Yes";
            this.fetchBaseApi();
        }
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    fetchBaseApi = async () => {
        try {
            this.setState({ baseApi: this.baseApi }, () => {
                this.fetchCurrentWeather();
                this.fetchDailySummary();
                this.interval = setInterval(this.fetchCurrentWeather, 300000); // fetch every 5 minutes
            });
        } catch (error) {
            console.log('Error fetching base API:', error);
        }
    };

    fetchCurrentWeather = async () => {
        try {
            const { city, baseApi } = this.state;
            if (baseApi) {
                const response = await axios.get(`${baseApi}/api/weather/current/${city}`);
                console.log("Response data:", response.data);
                if (typeof response.data === "object") {
                    this.setState({ weather: response.data });
                } else {
                    console.error("Expected JSON, but got:", response.data);
                }
            }
        } catch (error) {
            console.error('Error fetching current weather:', error.response ? error.response.data : error.message);
        }
    };


    fetchDailySummary = async () => {
        try {
            const { city, baseApi } = this.state;
            if (baseApi) {
                const response = await axios.get(`${baseApi}/api/weather/daily-summary/${city}`);
                if (Array.isArray(response.data)) {
                    this.setState({ summary: response.data });
                } else {
                    console.warn("Invalid daily summary response:", response.data);
                }
            }
        } catch (error) {
            console.error('Error fetching daily summary:', error.response ? error.response.data : error.message);
        }
    };


    setTemperatureAlert = async () => {
        try {
            const { city, threshold, baseApi } = this.state;
            if (baseApi) {
                const response = await axios.post(`${baseApi}/api/weather/set-alert/${city}`, { threshold: Number(threshold) });
                let messageClass = "badge ";
                const [message, status] = response.data || ["", ""];

                messageClass += status === "YES" ? "bg-success" : "bg-danger";
                this.setState({ alertMessage: message, alertMessageClass: messageClass });
                console.log(`Alert Response: ${response.data}`);
            }
        } catch (error) {
            console.log('Error setting alert:', error);
        }
    };

    handleCityChange = (event) => {
        const city = event.target.value.toUpperCase();
        this.setState({ city }, () => {
            this.fetchCurrentWeather();
            this.fetchDailySummary();
        });
        ReactGA.event({
            category: "WeatherMonitor",
            action: "City Changed",
            label: city
        });
    };

    handleThresholdChange = (event) => {
        this.setState({ threshold: event.target.value });
    };

    render() {
        const { city, weather, summary, threshold, alertMessage, alertMessageClass } = this.state;

        return (
            <div className="text-center">
                <h1>Weather Monitoring</h1>
                <div>
                    <label>Select City: </label>
                    <select value={city} onChange={this.handleCityChange}>
                        <option value="DELHI">Delhi</option>
                        <option value="MUMBAI">Mumbai</option>
                        <option value="CHENNAI">Chennai</option>
                        <option value="BANGALORE">Bangalore</option>
                        <option value="KOLKATA">Kolkata</option>
                        <option value="HYDERABAD">Hyderabad</option>
                    </select>
                </div>

                {weather && (
                    <div className="weather-info">
                        <h2>Current Weather in {city}</h2>
                        <p>Temperature: {weather.temp.toFixed(2)}°C</p>
                        <p>Feels Like: {weather.feelsLike.toFixed(2)}°C</p>
                        <p>Condition: {weather.condition}</p>
                        <p>Wind Speed: {(weather.windSpeed * (18 / 5)).toFixed(2)} km/hr</p>
                        <p>Humidity: {weather.humidity}%</p>
                        <p>Sea Level: {weather.seaLevel} hPa</p>
                        <p>Visibility: {(weather.visibility / 1000).toFixed(2)} km</p>
                    </div>
                )}

                <div className="daily-summary">
                    <h2>Daily Summary for {city}</h2>
                    <ul>
                        {summary.map((day, index) => (
                            <ul key={index}>
                                Avg Temp: {day[0]}°C, Max Temp: {day[1]}°C, Min Temp: {day[2]}°C, Condition: {day[3]}
                            </ul>
                        ))}
                    </ul>
                </div>

                <div className="temperature-alert">
                    <h3>Set Temperature Alert</h3>
                    <div>
                        <input
                            type="number"
                            value={threshold}
                            onChange={this.handleThresholdChange}
                        />
                        <button type="button" className="btn btn-primary" onClick={this.setTemperatureAlert}>
                            Set Alert
                        </button>
                    </div>
                    {alertMessage && <p className={alertMessageClass}>{alertMessage}</p>}
                </div>
            </div>
        );
    }
}

export default WeatherMonitor;
