import React, { Component } from 'react';
import { Route, Switch, BrowserRouter, Link } from 'react-router-dom';
import Error from './Error';
import AddForm from './component/AddForm';
import Home from './component/Home';
import View from './component/View';

import ReactGA from "react-ga4";
import { initGA, sendPageView } from "./component/GoogleAnalytics";

class DailyReport extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    const gaID = process.env.REACT_APP_GA_MEASUREMENT_ID;
    initGA(gaID);

    // Initial page load
    sendPageView(window.location.pathname, "DailyReport Loaded");
  }

  componentDidUpdate(prevProps) {
    // Track route changes
    if (this.props.location !== prevProps.location) {
      sendPageView(window.location.pathname, "Route Changed");
    }
  }

  handleAddClick = () => {
    this.setState({ newRow: Array(6).fill(""), showAddRow: true });
  };

  render() {
    return (
      <BrowserRouter>
        <div>
          <h1 className="text-center"><u>Daily Report</u></h1>

          <button className="btn btn-outline-secondary ms-3 mb-2">
            <Link to="/">Home</Link>
          </button>

          <button className="btn btn-outline-secondary ms-3 mb-2" onClick={this.handleAddClick}>
            <Link to="/add-data">Add Information</Link>
          </button>

          <button className="btn btn-outline-secondary ms-3 mb-2">
            <Link to="/view">Show Data</Link>
          </button>

          <Switch>
            <Route exact path="/" component={Home} />
            <Route exact path="/view" render={() => <View pageName="View-page" />} />
            <Route exact path="/add-data" render={() => <AddForm pageName="add-data" />} />
            <Route path="*" component={Error} />
          </Switch>

        </div>
      </BrowserRouter>
    );
  }
}

export default DailyReport;
