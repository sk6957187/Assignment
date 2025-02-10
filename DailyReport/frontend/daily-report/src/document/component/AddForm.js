import React, { Component } from 'react'

class AddForm extends Component {

    constructor(props) {
        super(props);
        var newRow = Array(6).fill("");
        newRow[5] = "NO";
        this.state = {
          newRow: newRow
        };
        this.editingRowIndex=null;
        this.showAddRow = this.props.showAddRow;
        if (this.props.pageName === "add-data") {
            this.showAddRow = true;
        }
        // this.editedRow=null;
      }

    handleNewRowChange = (index, value) => {
        const updatedRow = [...this.state.newRow];
        updatedRow[index] = value;
        this.setState({ newRow: updatedRow });
      };

      handleAddRowSubmit = async () => {
        const { newRow } = this.state;
        // Check for empty values
        let emptyIndex = newRow.findIndex((cell, index) => index !== 5 && cell.trim() === "");
        if (emptyIndex !== -1) {
          alert(`Field at index ${emptyIndex+1} is required!`);
          return;
        }
    
        try {
          console.log("Added data: ",newRow);
          const response = await fetch("http://localhost:8080/daily-report/add", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(newRow),
          });
    
          if (response.ok) {
            const message = await response.text();
            this.setState({
              newRow: [],
            });
            this.showAddRow = false;
            alert(message);
            if (message === "Record added successfully!") {
                window.location.href = "/view"; // Redirect to the /view page
            }
          } else {
            throw new Error(`Failed to add row: ${response.statusText}`);
          }
        } catch (error) {
          console.error("Error adding row:", error);
          alert("Error adding row: " + error.message);
        }
      };

  render() {
    var newRow = this.state.newRow;
    return (
      <div>
        <table className="table">
        <thead>
          <tr>
            <th>START_DATE</th>
            <th>USERID</th>
            <th>SUB</th>
            <th>TOPIC</th>
            <th>TOPIC_DETAILS</th>
            <th>COMPLETED</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            {newRow.map((cell, index) => (
              <td key={index}>
              {index === 5 ? (
              <select
                className="form-control bg-info focus"
                onChange={(e) => this.handleNewRowChange(index, e.target.value)}>
                  <option value="NO">NO</option>
                  <option value="YES">YES</option>
              </select>
              // <input type="text" className="form-control bg-info focus" value={cell} onChange={(e) => this.handleNewRowChange(index, e.target.value)}/>
              ) : index === 0 ? (
                <input type="date" className="form-control bg-info focus" value={cell} onChange={(e) => this.handleNewRowChange(index, e.target.value)}/>
                ):(  
                <input type="text" className="form-control bg-info focus" value={cell} onChange={(e) => this.handleNewRowChange(index, e.target.value)}/>
              )}
            </td>
            ))}
            <td>
              <button type="button"  className="btn btn-success" onClick={this.handleAddRowSubmit}>
                Save
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      </div>
    )
  }
}

export default AddForm
