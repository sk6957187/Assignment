import React,  { Component } from "react";
import InputForm from "./InputForm"; 



export class Home extends Component {
  constructor(props) {
    super(props);
    this.state = { showForm: false };
  }

  toggleForm = () => {
    this.setState({ showForm: !this.state.showForm });
  };

  navbar() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <span>
            <a className="navbar-brand" href="#">Navbar</a>
          </span>
          <button onClick={this.toggleForm} className="btn btn-primary">
            Add Form
          </button>
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
      </div>
    );
  }
}

export default Home;
