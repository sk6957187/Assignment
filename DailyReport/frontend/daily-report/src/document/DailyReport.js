import React, { Component } from 'react';

class DailyReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      isLoading: true,
      error: null,
      editingRowIndex: null, // Tracks which row is being edited
      editedRow: null,       // Stores the data being edited
    };
  }

  async componentDidMount() {
    try {
      const response = await fetch("http://localhost:8080/daily-report/view");
      if (!response.ok) {
        throw new Error(`Network response was not ok: ${response.statusText}`);
      }
      const data = await response.json();
      this.setState({ data, isLoading: false });
    } catch (error) {
      console.error("Error fetching data:", error);
      this.setState({ error, isLoading: false });
    }
  }

  handleEditClick = (index, row) => {
    const editableRow = row.map((cell, cellIndex) => {
      // Convert dates to ISO format for editing
      if (cellIndex === 7 || cellIndex === 8) {
        return cell ? new Date(cell).toISOString().slice(0, 10) : "";
      }
      return cell;
    });

    this.setState({ editingRowIndex: index, editedRow: editableRow });
  };

  handleInputChange = (index, value) => {
    const updatedRow = [...this.state.editedRow];
    updatedRow[index] = value; // Update the specific cell value
    this.setState({ editedRow: updatedRow });
  };

  handleUpdateSubmit = async () => {
    const { data, editingRowIndex, editedRow } = this.state;

    try {
      const response = await fetch("http://localhost:8080/daily-report/update", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(editedRow),
      });

      if (response.ok) {
        const message = await response.text();
        console.log(message);
        const updatedData = [...data];
        updatedData[editingRowIndex] = editedRow; // Update row in the state
        this.setState({
          data: updatedData,
          editingRowIndex: null,
          editedRow: null,
        });
        alert(message); // Show success message
      } else {
        throw new Error(`Failed to update: ${response.statusText}`);
      }
    } catch (error) {
      console.error("Error updating row:", error);
      alert("Error updating row: " + error.message);
    }
  };

  renderTable() {
    const { data, editingRowIndex, editedRow } = this.state;

    return (
      <table border="1" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>SNO</th>
            <th>START_DATE</th>
            <th>USERID</th>
            <th>SUB</th>
            <th>TOPIC</th>
            <th>TOPIC_DETAILS</th>
            <th>COMPLETED</th>
            <th>ADDED_DATE</th>
            <th>UPDATE_TIME</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => (
            <tr key={index}>
              {row.map((cell, cellIndex) => (
                <td key={cellIndex}>
                  {editingRowIndex === index ? (cellIndex==0 || cellIndex === 7 || cellIndex === 8 ? (
                      // no update here
                      <span>{editedRow[cellIndex]}</span>
                    ) : (
                      <input
                        type="text"
                        value={editedRow[cellIndex]}
                        onChange={(e) =>
                          this.handleInputChange(cellIndex, e.target.value)
                        }
                      />
                    )
                  ) : (
                    cell
                  )}
                </td>
              ))}
              <td>
                {editingRowIndex === index ? (
                  <button type="button" className="btn btn-primary" onClick={this.handleUpdateSubmit}>Save</button>
                ) : (
                  <button type="button" className="btn btn-primary" onClick={() => this.handleEditClick(index, row)}>
                    Update
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  }

  render() {
    const { isLoading, error } = this.state;

    if (isLoading) {
      return <div>Loading...</div>;
    }
    if (error) {
      return <div>Error: {error.message}</div>;
    }
    return (
      <div>
        <h1>Daily Report</h1>
        {this.renderTable()}
      </div>
    );
  }
}

export default DailyReport;