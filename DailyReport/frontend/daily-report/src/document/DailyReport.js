import React, { Component } from 'react';
import {BrowserRouter, Link} from 'react-router-dom';
import { Route, Switch } from 'react-router-dom';
import Error from './Error';
import AddForm from './component/AddForm';
import Home from './component/Home';

class DailyReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      isLoading: true,
      error: null,
      newRow: [],
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

  handleEditClick = (index, row) => {
    // const editableRow = row.map((cell, cellIndex) => {
      // Convert dates to ISO format for editing
    //   if (cellIndex === 7 || cellIndex === 8) {
    //     return cell ? new Date(cell).toISOString().slice(0, 10) : "";
    //   }
    //   return cell;
    // });
      this.editingRowIndex = index;
    //   this.editedRow=row;
      this.setState({editedRow: row});
    // this.setState({ editingRowIndex: index, editedRow: editableRow });
  };

  handleInputChange = (index, value) => {
    const updatedRow = [...this.state.editedRow];
    updatedRow[index] = value; // Update the specific cell value
    this.setState({ editedRow: updatedRow });
  };

  handleAddClick=() =>{
    this.setState({  newRow: Array(6).fill("") });
    this.showAddRow = true;
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
  
  
  getEditedRow = (editingRowIndex) => {
    // var row = [];
    // const { data } = this.state;
    // for(var i = 0; i<data.length; i++){
    //   if(i === editingRowIndex) {
    //     return data[i];
    //   }
    // }
    return this.state.editedRow;
  };

  handleUpdateSubmit = async () => {
    const { data, editedRow } = this.state;
    var editingRowIndex = this.editingRowIndex;
    // var editedRow = this.getEditedRow(editingRowIndex);
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
                  <button type="button" className="btn btn-primary me-2" onClick={this.handleUpdateSubmit}>Save</button>
                ) : (
                  <button type="button" className="btn btn-primary me-2" onClick={() => this.handleEditClick(index, row)}>
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
  //const View = () => <div>{this.renderTable()}</div>;



  render() {
    const { isLoading, error  } = this.state;
    var showAddRow = this.showAddRow;
    if (isLoading) {
      return <div className="text-center">
        <h2>Loading...</h2>
      </div>;
    }
    if (error) {
      return <h2>Error: {error.message}</h2>;
    }
    return (
      <BrowserRouter>
      <div>
        <h1 className="text-center"><u>Daily Report</u></h1>
        <button type="button" className="btn btn-outline-secondary float-none ms-3 mb-2" onClick={() => this.handleAddClick()}>
            <Link to="/add-data"> Add Information</Link>
        </button>
        <button type="button" className="btn btn-outline-secondary float-none ms-3 mb-2" onClick={() => this.renderTable()}>
            <Link to="/view">Show Data</Link>
        </button>
        {/* {this.renderTable()} */}
        <Switch>
            <Route exact path="/" component={Home} />
            <Route exact path="/view" render={() => this.renderTable()} />
            <Route exact path="/add-data" 
                render={props => (
                    <AddForm pageName="add-data" showAddRow=""/>
                )} />
          <Route path="*" component={Error} />
        </Switch>
      </div>
      </BrowserRouter>
    );
  }
}

export default DailyReport;
