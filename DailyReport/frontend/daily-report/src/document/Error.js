import React, { Component } from 'react';
import ReactGA from 'react-ga4';
import { initGA } from './component/GoogleAnalytics';

class Error extends Component {
  componentDidMount() {
    const gaID = process.env.REACT_APP_GA_MEASUREMENT_ID;
    initGA(gaID);
    console.log("GA ID:", gaID);

    ReactGA.send({
      hitType: "pageview",
      page: window.location.pathname,
      title: "Error.jsx",
    });
  }

  render() {
    return (
      <div>
        <button type="button" className="btn btn-outline-secondary ms-3 mb-2" onclick="window.location.href='/'">
          <a href="/" className="text-decoration-none">Home</a>
        </button>
        <h3 className="mt-3">404 Page not found!</h3>

      </div>
    )
  }
}

export default Error
