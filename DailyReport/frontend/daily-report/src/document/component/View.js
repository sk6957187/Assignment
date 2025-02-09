import React, { Component } from 'react';
import Pagination from './Pagination';

class View extends Component {

    constructor(props) {
        super(props);
        this.state = {
          data: [],
          isLoading: true,
          error: null,
          editedRow: [],
          filteredData: [], // To hold the filtered data for search
          searchQuery: '',
          record : [],
          searchQuerySno:'',
          searchQuerySub:'',
          currentPage: 1,
          rowsPerPage: 10, // Limit rows per page to 10
        };
        this.editingRowIndex=null;
    }

    async componentDidMount() {
      try {
        const response = await fetch("http://localhost:8080/daily-report/view");
        if (!response.ok) {
          throw new Error(`Network response was not ok: ${response.statusText}`);
        }
        const data = await response.json();
        this.setState({ data, record: data, isLoading: false });
        console.log("Data fetched:", data);
      } catch (error) {
        console.error("Error fetching data:", error);
        this.setState({ error, isLoading: false });
      }
    }

    
    setCurrentPage = (pageNumber) => {
      console.log("Setting current page:", pageNumber);
      this.setState({ currentPage: pageNumber }, () => {
        console.log("Updated current page:", this.state.currentPage);
      });
      console.log("Uuuuupdated current page:", this.state.currentPage);
    };
    
    
    filterPerPage = (e) => {
      let value = Number(e.target.value);
      if(value <= 1){
        value = 1;
      }
      this.setState({ rowsPerPage: value}); // Reset currentPage when rowsPerPage changes
    };
    
    reset = () =>{
      this.setState({
        searchQuerySno:'',
        searchQuerySub:'',
      });
    };

    filterBySno = (event) => {
      const searchValue = event.target.value.toString();
      this.setState({
        searchQuerySno: searchValue,
        record: this.state.data.filter((row) => row[0] && row[0].includes(searchValue)),
      });
    };
    
    filterBySub = (event) => {
      const searchValue = event.target.value.toLowerCase();
      this.setState({
        searchQuerySub: searchValue,
        record: this.state.data.filter((f) => f[3] && f[3].toLowerCase().includes(searchValue)),
      })
    };

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
        const isConfirmed = window.confirm(`Are you sure, you want to delete sno:${sno} ?`);
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
            this.setState({ data: updatedData, record: updatedData });
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
                alert(message); // Show success message
            } else {
                throw new Error(`Failed to update: ${response.statusText}`);
            }
        } catch (error) {
          console.error("Error updating row:", error);
          alert("Error updating row: " + error.message);
        }
    };
    
    highlightText = (text, query) => {
      if (!query) return text; // If no query, return the original text.
      const parts = text.split(new RegExp(`(${query})`, 'gi')); // Split text by the query.
      return parts.map((part, index) => 
        part.toLowerCase() === query.toLowerCase() ? <span key={index} style={{ backgroundColor: 'yellow' }}>{part}</span> : part
      );
    };
    

    renderTable() {
      const { record, searchQuerySno, searchQuerySub, sortColumn, currentPage, rowsPerPage } = this.state;
      var indexOfLastRow = currentPage * rowsPerPage;
      var indexOfFirstRow = indexOfLastRow - rowsPerPage;
      var currentRows = record.slice(indexOfFirstRow, indexOfLastRow);
      var editingRowIndex = this.editingRowIndex;
      var editedRow = this.getEditedRow(editingRowIndex);
    
      const handleSort = (columnIndex) => {
        const newDirection = sortColumn === columnIndex && this.state.sortDirection === 'asc' ? 'desc' : 'asc';
        const sortedData = [...record].sort((a, b) => {
          if (a[columnIndex] === undefined || b[columnIndex] === undefined) return 0; // Handle undefined values
          let valueA = a[columnIndex];
          let valueB = b[columnIndex];
          if (columnIndex === 0) {
            valueA = Number(valueA); // Convert to number
            valueB = Number(valueB); // Convert to number
          }
          if (newDirection === 'asc') {
            return valueA < valueB ? -1 : valueA > valueB ? 1 : 0;
          } else {
            return valueB < valueA ? -1 : valueB > valueA ? 1 : 0;
          }
        });
        this.setState({ record: sortedData, sortColumn: columnIndex, sortDirection: newDirection });
      };
      
      const getSortableIcon = (columnIndex) => (
        <span
          style={{
            marginLeft: '5px',
            color: sortColumn === columnIndex ? 'blue' : 'gray',
            cursor: 'pointer',
          }}
        >
        ⇅
        </span>
      );

      return (
        <table className="table">
          <thead className="headDiv">
            <tr>
              <th>#</th>
              <th onClick={() => handleSort(0)} style={{ cursor: 'pointer' }}>
                SNO {getSortableIcon(0)}
              </th>
              <th onClick={() => handleSort(1)} style={{ cursor: 'pointer' }}>
                START_DATE {getSortableIcon(1)}
              </th>
              <th onClick={() => handleSort(3)} style={{ cursor: 'pointer' }}>
                USERID {getSortableIcon(3)}
              </th>
              <th onClick={() => handleSort(4)} style={{ cursor: 'pointer' }}>
                SUB {getSortableIcon(4)}
              </th>
              <th>TOPIC</th>
              <th>TOPIC_DETAILS</th>
              <th>COMPLETED</th>
              <th>ADDED_DATE</th>
              <th>UPDATE_TIME</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {currentRows.map((row, index) => (
              <tr key={index}>
                <td>{indexOfFirstRow + index + 1}</td>
                {row.map((cell, cellIndex) => (
                  <td key={cellIndex}>
                    {editingRowIndex === index ? (
                      cellIndex === 0 || cellIndex === 7 || cellIndex === 8 ? (
                        <span>{editedRow[cellIndex]}</span>
                      )  : cellIndex === 6 ? (
                        <select className="form-control bg-info focus" value={cell} onChange={(e) => this.handleNewRowChange(index, e.target.value)}>
                        <option value="NO">No</option>
                        <option value="YES">Yes</option>
                      </select>
                      ) : (
                        <input
                          type="text" className="form-control bg-info focus" value={editedRow[cellIndex]} onChange={(e) => this.handleInputChange(cellIndex, e.target.value)}
                        />
                      )
                    ) : cellIndex === 0 ? (
                      this.highlightText(cell, searchQuerySno)
                    ) : cellIndex === 3 ? (
                      this.highlightText(cell, searchQuerySub)
                    ) : (
                      cell
                    )}
                  </td>
                ))}
                <td>
                  {editingRowIndex === index ? (
                    <button type="button" className="btn btn-primary me-2 mb-2" onClick={this.handleUpdateSubmit}>
                      Save
                    </button>
                  ) : (
                    <button type="button" className="btn btn-primary me-2 mb-2" onClick={() => this.handleEditClick(index, row)}>
                      Update
                    </button>
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
    }

    searchHandle() {
        var { searchQuerySno, searchQuerySub } = this.state;
        return(
          <div className="d-flex justify-content-end me-3 gap-2">
            <input type="text" placeholder="search by sno" value={searchQuerySno} className="form-control" style={{ maxWidth: "200px" }} onChange={this.filterBySno} />
            <button className="btn btn-primary" style={{ marginLeft: "10px" }} onClick={this.filterBySno}> search by SNO</button>     {/* This button is not work */}
            <input type="text" placeholder="search by sub" value={searchQuerySub} className="form-control" style={{ maxWidth: "200px" }} onChange={this.filterBySub} />
            <button className="btn btn-primary" onClick={this.reset}>Reset</button>
        </div>
        )
    };

    render() {
        const { isLoading, error, record, rowsPerPage, currentPage } = this.state;
    
        if (isLoading) return <h2>Loading...</h2>;
        if (error) return <h2>Error: {error.message}</h2>;
    
        return (
          <div>
            <div>{this.searchHandle()}</div>
            <div>{this.renderTable()}</div>
            <div>
              <Pagination
                totalPost={record.length}
                currentPage={currentPage}
                postsPerPage={rowsPerPage}
                setCurrentPage={this.setCurrentPage}
                filterPerPage={this.filterPerPage} // Pass filterPerPage to update postsPerPage
              />
          </div>
          </div>
        );
    }
}

export default View;