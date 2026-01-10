import React, { Component } from 'react';
import ReactGA from 'react-ga4';
import { initGA } from './GoogleAnalytics.js';

class Home extends Component {

  componentDidMount() {
    const gaID = process.env.REACT_APP_GA_MEASUREMENT_ID;
    initGA(gaID);
    ReactGA.send({
      hitType: "pageview",
      page: window.location.pathname,
      title: "Home.jsx",
    });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Welcome To Home page.</h2>
        <button type="button" className="btn btn-primary float-end me-2 mb-2">
          Setting
        </button>
      </div>
    );
  }
}

export default Home;
