import React, { Component } from 'react';
import {BrowserRouter, Link} from 'react-router-dom';
import { Route, Switch } from 'react-router-dom';
import Error from './Error';
import AddForm from './component/AddForm';
import Home from './component/Home';
import View from './component/View';

class DailyReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      isLoading: true,
      error: null,
      newRow: [],
      editedRow: []
    };
    this.editingRowIndex=null;
    this.showAddRow = false;
    // this.editedRow=null;
  }


  handleAddClick = () => {
    this.setState({ newRow: Array(6).fill(""), showAddRow: true });
  };
  

  

  render() {
    const { error  } = this.state;
    var showAddRow = this.showAddRow;
    if (error) {
      return <h2>Error: {error.message}</h2>;
    }
    return (
      <BrowserRouter>
      <div>
        <h1 className="text-center"><u>Daily Report</u></h1>
        <button className="btn btn-outline-secondary float-none ms-3 mb-2">
            <Link to="/">Home</Link>
        </button>
        <button type="button" className="btn btn-outline-secondary float-none ms-3 mb-2" onClick={() => this.handleAddClick()}>
            <Link to="/add-data"> Add Information</Link>
        </button>
        <button type="button" className="btn btn-outline-secondary float-none ms-3 mb-2" >
            <Link to="/view">Show Data</Link>
        </button>
        {/* {this.renderTable()} */}
        <Switch>
            <Route exact path="/" component={Home} />
            <Route exact path="/view" render={ props => (
                <View pageName="View-page" />
            )} />
            <Route exact path="/add-data" 
                render={props => (
                    <AddForm pageName="add-data" showAddRow=""/>
                )} />
          <Route path="*" component={Error} />
        </Switch>
      </div>
      </BrowserRouter>
    );
  }
}

export default DailyReport;
