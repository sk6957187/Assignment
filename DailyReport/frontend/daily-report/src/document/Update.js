import React, { Component } from 'react';

class Update extends Component {
  constructor(props) {
    super(props);

    // Parse the rowData from the query string
    const params = new URLSearchParams(window.location.search);
    const rowData = JSON.parse(decodeURIComponent(params.get('rowData') || '[]'));

    this.state = {
      rowData,
      successMessage: null,
      errorMessage: null,
    };
  }

  handleInputChange = (index, value) => {
    const updatedRow = [...this.state.rowData];
    updatedRow[index] = value;
    this.setState({ rowData: updatedRow });
  };

  handleSubmit = async (e) => {
    e.preventDefault();
    const { rowData } = this.state;

    try {
      const response = await fetch("http://localhost:8080/daily-report/update", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(rowData),
      });

      if (response.ok) {
        this.setState({ successMessage: "Update successful!" });
      } else {
        throw new Error(`Failed to update: ${response.statusText}`);
      }
    } catch (error) {
      console.error("Error updating data:", error);
      this.setState({ errorMessage: error.message });
    }
  };

  render() {
    const { rowData, successMessage, errorMessage } = this.state;

    return (
      <div>
        <h1>Update Record</h1>
        <form onSubmit={this.handleSubmit}>
          {rowData.map((value, index) => (
            <div key={index}>
              <label>
                Field {index + 1}:
                <input
                  type="text"
                  value={value}
                  onChange={(e) => this.handleInputChange(index, e.target.value)}
                />
              </label>
            </div>
          ))}
          <button type="submit">Submit</button>
        </form>
        {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
      </div>
    );
  }
}

export default Update;
