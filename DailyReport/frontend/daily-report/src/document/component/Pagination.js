import React, { Component } from "react";
import {Link} from 'react-router-dom';


class Pagination extends Component {
  constructor(props) {
    super(props);
    this.state = {
      postsPerPage: props.postsPerPage,
      pageNumber: ""
    };
  }

  componentDidUpdate(prevProps) {
    if (prevProps.totalPost !== this.props.totalPost || prevProps.postsPerPage !== this.props.postsPerPage) {
      this.forceUpdate(); // Forces re-render when totalPost or postsPerPage changes
    }
  }

  handlePageChange = (pageNumber) => {
    console.log("Page clicked:", pageNumber);
    this.setState({pageNumber:pageNumber}); 
    this.props.setCurrentPage(pageNumber);
  };

  filterPerPage = (e) => {
    const value = Number(e.target.value);
    this.setState({ postsPerPage: value });
    this.props.filterPerPage(e);
  };

  render() {
    const { totalPost, currentPage, postsPerPage } = this.props;
    const totalPages = Math.ceil(totalPost / postsPerPage);
    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
      <div style={{ display: "flex", alignItems: "center" }}>
        {pageNumbers.map((number) => (
          <Link to="/view" key={number}>
            <button
              onClick={() => this.handlePageChange(number)}
              style={{
                margin: "5px",
                backgroundColor: number === currentPage ? "blue" : "white",
                color: number === currentPage ? "white" : "black",
              }}
            >
              {number}
            </button>
          </Link>
        ))}

        <span style={{ marginLeft: "10px" }}>Posts per page</span>
        <span style={{ marginLeft: "5px" }}> <select className="form-control bg-info" onChange={this.filterPerPage}>
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select> </span>
      </div>
    );
  }
}

export default Pagination;
