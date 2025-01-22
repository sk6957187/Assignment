import React, { Component } from "react";

class Pagination extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pageNumbers: [],
      postsPerPage: this.props.postsPerPage,
    };
  }

  static getDerivedStateFromProps(nextProps, nextState) {
    const pageNumbers = [];
    const { totalPost, postsPerPage } = nextProps;
    const totalPages = Math.ceil(totalPost / postsPerPage);
    for (let i = 1; i <= totalPages; i++) {
      pageNumbers.push(i);
    }
    return { pageNumbers };
  }

  handlePageChange = (pageNumber) => {
    console.log("Page clicked:", pageNumber);
    this.props.setCurrentPage(pageNumber);
  };

  filterPerPage = (e) => {
    const value = Number(e.target.value);
    this.setState({ postsPerPage: value });
    this.props.filterPerPage(e); // Notify the parent component about the change
  };
  

  render() {
    const { pageNumbers } = this.state;
    const { currentPage} = this.props;

    return (
      <div style={{ display: 'flex', alignItems: 'center' }}>
        {pageNumbers.map((number) => (
          <button
            key={number}
            onClick={() => {
              console.log("Button clicked for page:", number); // Debugging
              this.handlePageChange(number);
            }}
            style={{
              margin: "5px",
              backgroundColor: number === currentPage ? "blue" : "white",
              color: number === currentPage ? "white" : "black",
            }}
          >
            {number}
          </button>
        ))}
        <span style={{ marginLeft: "10px" }}>posts per page</span>
        <input type="number" className="form-control" style={{ marginLeft: "10px", width: "60px", height: "30px" }} value={this.state.postsPerPage} onChange={this.filterPerPage}/>
      </div>
    );
  }
}

export default Pagination;
