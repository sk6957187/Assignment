import React, { Component } from 'react'

class View extends Component {

    constructor(props) {
        super(props);
        this.state = {
          data: [],
          isLoading: true,
          error: null,
          editedRow: []
        };
        this.editingRowIndex=null;
        this.showAddRow = false;
        // this.editedRow=null;
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

    handleInputChange = (index, value) => {
        const updatedRow = [...this.state.editedRow];
        updatedRow[index] = value; // Update the specific cell value
        this.setState({ editedRow: updatedRow });
    };

    getEditedRow = (editingRowIndex) => {
        return this.state.editedRow;
    };

    handleEditClick = (index, row) => {
        
          this.editingRowIndex = index;
          this.setState({editedRow: row});
    };
    
    handleDelete = async (sno) => {
        const isConfirmed = window.confirm("Are you sure you want to delete this row?");
        if (!isConfirmed) {
          return;
        }
        const { data } = this.state;
        console.log("sno: ",sno);
        try {
          const response = await fetch(`http://localhost:8080/daily-report/delete`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(sno),
          });
          if (response.ok) {
            const message = await response.text();
            console.log(message);
            const updatedData = data.filter((row) => row[0] !== sno);
            this.setState({ data: updatedData });
            alert(message);
          } else {
            throw new Error(`Failed to delete: ${response.statusText}`);
          }
        } catch (error) {
          console.error("Error deleting row:", error);
          alert("Error deleting row: " + error.message);
        }
    };

    handleUpdateSubmit = async () => {
        const { data, editedRow } = this.state;
        var editingRowIndex = this.editingRowIndex;
        try {
            console.log(editedRow);
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
                editedRow: null,
                });
                this.editingRowIndex = null;
                // this.editedRow = null;
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

        const { data } = this.state;
        var editingRowIndex = this.editingRowIndex;
        var editedRow = this.getEditedRow(editingRowIndex);
    
        return (
          <table className="table">
            <thead>
              <tr>
                <th>#</th>
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
                    <td>{index + 1}</td>    {/* // the value of index is automatically passed by the map function and corresponds to the position of each row in the data array. */}
                    {row.map((cell, cellIndex) => (
                    <td key={cellIndex}>
                      {editingRowIndex === index ? (cellIndex ===0 || cellIndex === 7 || cellIndex === 8 ? (
                          // no update here
                          <span>{editedRow[cellIndex]}</span>
                        ) : (
                          <input type="text" className="form-control bg-info focus" value={editedRow[cellIndex]} onChange={(e) => this.handleInputChange(cellIndex, e.target.value)}/>
                        )
                      ) : (
                        cell
                      )}
                    </td>
                  ))}
                  <td>
                    {editingRowIndex === index ? (
                      <button type="button" className="btn btn-primary me-2 mb-2" onClick={this.handleUpdateSubmit}>Save</button>
                    ) : (
                      <button type="button" className="btn btn-primary me-2 mb-2" onClick={() => this.handleEditClick(index, row)}>
                        update
                      </button>
                      // <a type="button" href={"/app-view/edit/sno/" + row[0]} className="btn btn-primary me-md-2">Update</a>
                    )}
                    <button type="button" className="btn btn-danger" onClick={() => this.handleDelete(row[0])}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        );
    };
    
    searchHandle() {
        return(
          <div className="float-end me-3">
          <input type="text" placeholder="search by sno" />
          <button className="btn btn-primary">search</button>
        </div>
        
        )
    };

  render() {
    const { isLoading, error  } = this.state;
    if (isLoading) {
      return <div className="text-center">
        <h2>Loading...</h2>
      </div>;
    }
    if (error) {
      return <h2>Error: {error.message}</h2>;
    }
    return (
        <div>
            <div>{this.searchHandle()}</div>
            <div>{this.renderTable()}</div>
        </div>
    );      
  }
}

export default View
