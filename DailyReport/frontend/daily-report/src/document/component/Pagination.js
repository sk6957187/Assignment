import React, { Component } from "react";

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
          <button
            key={number}
            onClick={() => this.handlePageChange(number)}
            style={{
              margin: "5px",
              backgroundColor: number === currentPage ? "blue" : "white",
              color: number === currentPage ? "white" : "black",
            }}
          >
            {number}
          </button>
        ))}
        <span style={{ marginLeft: "10px" }}>Posts per page</span>
        <input
          type="number"
          className="form-control"
          style={{ marginLeft: "10px", width: "60px", height: "30px" }}
          value={this.state.postsPerPage}
          onChange={this.filterPerPage}
        />
      </div>
    );
  }
}

export default Pagination;
