import React,  { Component } from "react";
import InputForm from "./InputForm"; 
import ViewPage from "./ViewPage";



class Home extends Component {
  constructor(props) {
    super(props);
    this.state = { 
      showForm: false,
      showViewPage: false,
     };

  }

  toggleFormShowForm = () => {
  this.setState((prevState) => ({
    showForm: !prevState.showForm,
    showViewPage: false,
  }));
};

toggleFormViewPage = () => {
  this.setState((prevState) => ({
    showViewPage: !prevState.showViewPage,
    showForm: false,
  }));
};


  navbar() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <span>
            <a className="navbar-brand" href="#">Navbar</a>
          </span>
          <button onClick={this.toggleFormShowForm} className="btn btn-primary">
            Add Form
          </button>
          <button onClick={this.toggleFormViewPage} className="btn btn-primary">View</button>
          <form className="d-flex">
            <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
            <button className="btn btn-outline-success" type="submit">Search</button>
          </form>
        </div>
      </nav>
    );
  }

  render() {
    return (
      <div>
        {this.navbar()}
        {this.state.showForm && <InputForm />}
        {this.state.showViewPage && <ViewPage />}
      </div>
    );
  }
}

export default Home;
