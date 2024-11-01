import React, { Component } from 'react';
import axios from 'axios';

class WeatherMonitor extends Component {
    constructor(props) {
        super(props);
        this.state = {
            city: "DELHI",
            weather: null,
            summary: [],
            threshold: 35,
            alertMessage: '',
            alertMessageClass: ""
        };
        this.baseApi= '';
        this.staticDataApi="http://localhost:8080/api/weather/base-api";
        this.dataFeatchStatus="No";
    }
    

    componentDidMount() {
        if(this.dataFeatchStatus==="No"){
            this.dataFeatchStatus="Yes";
            this.fetchBaseApi();
            
        }
        
    }
    
    componentWillUnmount() {
        console.log(2);
        clearInterval(this.interval);
    }
    
    fetchBaseApi = async () => {
        try {
            const response = await axios.get(this.staticDataApi);
            this.baseApi = response.data;
            await this.fetchCurrentWeather();
            await this.fetchDailySummary();
            this.interval = setInterval(this.fetchCurrentWeather, 300000);      // fetch weather every 5 minutes
        } catch (error) {
            console.log('Error fetching base API:', error);
        }
    };
    

    fetchCurrentWeather = async () => {
        try {
            const { city, baseApi } = this.state;
            if (this.baseApi) {
                const response = await axios.get(`${this.baseApi}/api/weather/current/${city}`);
                this.setState({ weather: response.data });
                console.log(response.data);
            }
        } catch (error) {
            console.log('Error fetching current weather:', error);
        }
    };
    fetchDailySummary =  async () => {
        try {
            var { city, baseApi } = this.state;
            if (this.baseApi) {
                const response = await axios.get(`${this.baseApi}/api/weather/daily-summary/${city}`);
                this.setState({ summary: response.data });
                console.log(response.data);
            }
        } catch (error) {
            console.log('Error fetching daily summary:', error);
        }
    };

    setTemperatureAlert = async () => {
        try {
            const { city, threshold, baseApi } = this.state;
            if (this.baseApi) {
                const response = await axios.post(`${this.baseApi}/api/weather/set-alert/${city}`, {
                    threshold: Number(threshold),
                });
                var messageClass = "badge ";
                var message = "";
                var status ="";
                if(response.data && response.data.length === 2){
                    message = response.data[0];
                    status = response.data[1];
                }
                if(status === "YES"){
                    messageClass = messageClass + "bg-success";
                } else if(status === "NO"){
                    messageClass = messageClass + "bg-danger";
                }
                this.setState({ alertMessage: message, alertMessageClass: messageClass });
                console.log(`Response: ${response.data}`);
            }
        } catch (error) {
            console.log('Error setting alert:', error);
        }
    };

    handleCityChange = (event) => {
        this.setState({ city: event.target.value.toUpperCase() }, () => {
            this.fetchCurrentWeather();
            this.fetchDailySummary();
        });
    };

    handleThresholdChange = (event) => {
        this.setState({ threshold: event.target.value });
    };

    render() {
        const { city, weather, summary, threshold, alertMessage } = this.state;

        return (
            <div>
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
                    <div className="row">
                        <h2>Current Weather in {city}</h2>
                        <p>Temperature: {weather.temp.toFixed(2)}°C</p>
                        <p>Feels Like: {weather.feelsLike.toFixed(2)}°C</p>
                        <p>Condition: {weather.condition}</p>
                        <p>Wind Speed: {(weather.windSpeed * (18/5)).toFixed(2)} km/hr</p>
                        <p>Humidity: {weather.humidity}%</p>
                        <p>Sea Level: {weather.seaLevel} hPa</p>
                        <p>Visibility: {(weather.visibility / 1000).toFixed(2)} km</p>
                    </div>
                )}

                <div>
                    <h2>Daily Summary for {city}</h2>
                    <ul>
                        {summary.map((day, index) => (
                            <ul key={index}>
                                Avg Temp: {day[0]}°C, Max Temp: {day[1]}°C, Min Temp: {day[2]}°C, Condition: {day[3]}
                            </ul>
                        ))}
                    </ul>
                </div>

                <div>
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
                    {alertMessage && <p className={this.state.alertMessageClass}>{alertMessage}</p>}
                </div>
            </div>
        );
    }
}

export default WeatherMonitor;
