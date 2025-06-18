import React, { Component } from 'react';

class AddForm extends Component {
  constructor(props) {
    super(props);
    const newRow = Array(6).fill("");
    newRow[5] = "NO";

    this.state = {
      newRow: newRow,
      fileInputs: [null], // Initial file input
    };

    this.showAddRow = this.props.pageName === "add-data" ? true : this.props.showAddRow;
  }

  handleNewRowChange = (index, value) => {
    const updatedRow = [...this.state.newRow];
    updatedRow[index] = value;
    this.setState({ newRow: updatedRow });
  };

  handleFileChange = (e, index) => {
    const files = [...this.state.fileInputs];
    files[index] = e.target.files[0];
    this.setState({ fileInputs: files });
  };

  addFileInput = () => {
    this.setState((prevState) => ({
      fileInputs: [...prevState.fileInputs, null],
    }));
  };

  handleAddRowSubmit = async () => {
    const { newRow, fileInputs } = this.state;

    const emptyIndex = newRow.findIndex((cell, index) => index !== 5 && cell.trim() === "");
    if (emptyIndex !== -1) {
      alert(`Field at index ${emptyIndex + 1} is required!`);
      return;
    }

    try {
      const formData = new FormData();

      newRow.forEach((value) => {
        formData.append("addData", value);
      });

      fileInputs.forEach((file) => {
        if (file) {
          formData.append("mediaData", file);
        }
      });
      console.log(formData);
      
      const response = await fetch("http://localhost:8080/daily-report/add", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        const message = await response.text();
        this.setState({
          newRow: Array(6).fill("").map((v, i) => (i === 5 ? "NO" : "")),
          fileInputs: [null],
        });
        this.showAddRow = false;
        alert(message);
        if (message === "Record added successfully!") {
          window.location.href = "/view";
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
    const { newRow, fileInputs } = this.state;

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
                      value={cell}
                      onChange={(e) => this.handleNewRowChange(index, e.target.value)}
                    >
                      <option value="NO">NO</option>
                      <option value="YES">YES</option>
                    </select>
                  ) : index === 0 ? (
                    <input
                      type="date"
                      className="form-control bg-info focus"
                      value={cell}
                      onChange={(e) => this.handleNewRowChange(index, e.target.value)}
                    />
                  ) : (
                    <input
                      type="text"
                      className="form-control bg-info focus"
                      value={cell}
                      onChange={(e) => this.handleNewRowChange(index, e.target.value)}
                    />
                  )}
                </td>
              ))}
              <td>
                <button
                  type="button"
                  className="btn btn-success"
                  onClick={this.handleAddRowSubmit}
                >
                  Save
                </button>
              </td>
            </tr>
            <tr>
              <td colSpan={7}>
                <label>Upload file(s)</label>
                {fileInputs.map((file, index) => (
                  <div key={index} className="d-flex mb-2">
                    <input
                      type="file"
                      className="form-control"
                      required={index === 0}
                      onChange={(e) => this.handleFileChange(e, index)}
                    />
                  </div>
                ))}
                {fileInputs.length < 5 && (
                  <button
                    type="button"
                    className="btn btn-sm btn-outline-primary mt-1"
                    onClick={this.addFileInput}
                  >
                    +
                  </button>
                )}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }
}

export default AddForm;
