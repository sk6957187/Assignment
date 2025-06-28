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
      filteredData: [],
      searchQuery: '',
      record: [],
      searchQuerySno: '',
      searchQuerySub: '',
      currentPage: 1,
      rowsPerPage: 10,
      linkVisibleIndex: null,
    };
    this.editingRowIndex = null;
  }

  fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/daily-report/view");
      if (!response.ok) throw new Error(`Network response was not ok: ${response.statusText}`);
      const data = await response.json();
      this.setState({ data, record: data, isLoading: false });
    } catch (error) {
      console.error("Error fetching data:", error);
      this.setState({ error, isLoading: false });
    }
  };

  componentDidMount() {
    this.fetchData();
  }



  setCurrentPage = (pageNumber) => {
    this.setState({ currentPage: pageNumber });
  };

  filterPerPage = (e) => {
    let value = Number(e.target.value);
    if (value <= 1) value = 1;
    this.setState({ rowsPerPage: value });
  };

  reset = () => {
    this.setState({ searchQuerySno: '', searchQuerySub: '', record: this.state.data });
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
    });
  };

  handleInputChange = (index, value) => {
    const updatedRow = [...this.state.editedRow];
    updatedRow[index] = value;
    this.setState({ editedRow: updatedRow });
  };

  getEditedRow = () => {
    return this.state.editedRow;
  };

  handleEditClick = (index, row) => {
    this.editingRowIndex = index;
    this.setState({ editedRow: row });
  };

  handleDelete = async (sno) => {
    const isConfirmed = window.confirm(`Are you sure you want to delete sno:${sno} ?`);
    if (!isConfirmed) return;
    const { data } = this.state;
    try {
      const response = await fetch(`http://localhost:8080/daily-report/delete`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(sno),
      });
      if (response.ok) {
        const message = await response.text();
        const updatedData = data.filter((row) => row[0] !== sno);
        this.setState({ data: updatedData, record: updatedData });
        alert(message);
        await this.fetchData();
      } else throw new Error(`Failed to delete: ${response.statusText}`);
    } catch (error) {
      console.error("Error deleting row:", error);
      alert("Error deleting row: " + error.message);
    }
  };

  handleUpdateSubmit = async () => {
    const { data, editedRow } = this.state;
    const editingRowIndex = this.editingRowIndex;
    try {
      const response = await fetch("http://localhost:8080/daily-report/update", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(editedRow),
      });
      if (response.ok) {
        const message = await response.text();
        const updatedData = [...data];
        updatedData[editingRowIndex] = editedRow;
        this.setState({ data: updatedData, editedRow: null });
        this.editingRowIndex = null;
        alert(message);
        await this.fetchData();
      } else throw new Error(`Failed to update: ${response.statusText}`);
    } catch (error) {
      console.error("Error updating row:", error);
      alert("Error updating row: " + error.message);
    }
  };

  handleLinkToggle = (index) => {
    this.setState(prev => ({
      linkVisibleIndex: prev.linkVisibleIndex === index ? null : index
    }));
  };

  highlightText = (text, query) => {
    if (!query) return text;
    const parts = text.split(new RegExp(`(${query})`, 'gi'));
    return parts.map((part, i) =>
      part.toLowerCase() === query.toLowerCase()
        ? <span key={i} style={{ backgroundColor: 'yellow' }}>{part}</span>
        : part
    );
  };

  renderTable() {
    const { record, searchQuerySno, searchQuerySub, sortColumn, currentPage, rowsPerPage, linkVisibleIndex } = this.state;
    const indexOfLastRow = currentPage * rowsPerPage;
    const indexOfFirstRow = indexOfLastRow - rowsPerPage;
    const currentRows = record.slice(indexOfFirstRow, indexOfLastRow);
    const editingRowIndex = this.editingRowIndex;
    const editedRow = this.getEditedRow();

    const handleSort = (columnIndex) => {
      const newDirection = sortColumn === columnIndex && this.state.sortDirection === 'asc' ? 'desc' : 'asc';
      const sortedData = [...record].sort((a, b) => {
        let valueA = a[columnIndex];
        let valueB = b[columnIndex];
        if (columnIndex === 0) {
          valueA = Number(valueA);
          valueB = Number(valueB);
        }
        if (newDirection === 'asc') return valueA < valueB ? -1 : valueA > valueB ? 1 : 0;
        else return valueB < valueA ? -1 : valueB > valueA ? 1 : 0;
      });
      this.setState({ record: sortedData, sortColumn: columnIndex, sortDirection: newDirection });
    };

    const getSortableIcon = (columnIndex) => (
      <span style={{ marginLeft: '5px', color: sortColumn === columnIndex ? 'blue' : 'gray', cursor: 'pointer' }}>⇅</span>
    );

    return (
      <table className="table">
        <thead className="headDiv">
          <tr>
            <th>#</th>
            <th onClick={() => handleSort(0)} style={{ cursor: 'pointer' }}>SNO {getSortableIcon(0)}</th>
            <th onClick={() => handleSort(1)} style={{ cursor: 'pointer' }}>START_DATE {getSortableIcon(1)}</th>
            <th onClick={() => handleSort(3)} style={{ cursor: 'pointer' }}>USERID {getSortableIcon(3)}</th>
            <th onClick={() => handleSort(4)} style={{ cursor: 'pointer' }}>SUB {getSortableIcon(4)}</th>
            <th>TOPIC</th>
            <th>TOPIC_DETAILS</th>
            <th>COMPLETED</th>
            <th>ADDED_DATE</th>
            <th>UPDATE_TIME</th>
            <th>Link</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {currentRows.map((row, index) => (
            <React.Fragment key={index}>
              <tr>
                <td>{indexOfFirstRow + index + 1}</td>
                {row.map((cell, cellIndex) => (
                  <td key={cellIndex}>
                    {editingRowIndex === index ? (
                      cellIndex === 0 || cellIndex === 7 || cellIndex === 8 ? (
                        <span>{editedRow[cellIndex]}</span>
                      ) : cellIndex === 6 ? (
                        <select className="form-control bg-info focus" value={cell} onChange={(e) => this.handleInputChange(cellIndex, e.target.value)}>
                          <option value="NO">No</option>
                          <option value="YES">Yes</option>
                        </select>
                      ) : (
                        <input type="text" className="form-control bg-info focus" value={editedRow[cellIndex]} onChange={(e) => this.handleInputChange(cellIndex, e.target.value)} />
                      )
                    ) : cellIndex === 0 ? (
                      this.highlightText(cell, searchQuerySno)
                    ) : cellIndex === 3 ? (
                      this.highlightText(cell, searchQuerySub)
                      ) : cellIndex === 9 && cell && cell.trim() !== "" ? (
                        <span style={{ cursor: 'pointer', color: 'blue' }} onClick={() => this.handleLinkToggle(index)}>🔗</span>
                      ) : (
                      cell
                    )}
                  </td>
                ))}
                <td>
                  {editingRowIndex === index ? (
                    <button type="button" className="btn btn-primary me-2 mb-2" onClick={this.handleUpdateSubmit}>Save</button>
                  ) : (
                    <button type="button" className="btn btn-primary me-2 mb-2" onClick={() => this.handleEditClick(index, row)}>Update</button>
                  )}
                  <button type="button" className="btn btn-danger" onClick={() => this.handleDelete(row[0])}>Delete</button>
                </td>
              </tr>
                  {row[9] && row[9].trim() !== "" && linkVisibleIndex === index && (
                    <tr>
                      <td colSpan={12}>
                        <strong>Links:</strong>
                        <ul style={{ paddingLeft: '1.2rem' }}>
                          {row[9]
                            .split(',')
                            .map(link => link.trim())
                            .filter(link => link !== "")
                            .map((link, idx) => (
                              <li key={idx}>
                                <a href={link} target="_blank" rel="noopener noreferrer">
                                  {link}
                                </a>
                              </li>
                            ))}
                        </ul>
                      </td>
                    </tr>
                  )}


            </React.Fragment>
          ))}
        </tbody>
      </table>
    );
  }

  searchHandle() {
    const { searchQuerySno, searchQuerySub } = this.state;
    return (
      <div className="d-flex justify-content-end me-3 gap-2">
        <input type="text" placeholder="search by sno" value={searchQuerySno} className="form-control" style={{ maxWidth: "200px" }} onChange={this.filterBySno} />
        <input type="text" placeholder="search by sub" value={searchQuerySub} className="form-control" style={{ maxWidth: "200px" }} onChange={this.filterBySub} />
        <button className="btn btn-primary" onClick={this.reset}>Reset</button>
      </div>
    );
  }

  render() {
    const { isLoading, error, record, rowsPerPage, currentPage } = this.state;
    if (isLoading) return <h2>Loading...</h2>;
    if (error) return <h2>Error: {error.message}</h2>;
    return (
      <div>
        {this.searchHandle()}
        {this.renderTable()}
        <Pagination
          totalPost={record.length}
          currentPage={currentPage}
          postsPerPage={rowsPerPage}
          setCurrentPage={this.setCurrentPage}
          filterPerPage={this.filterPerPage}
        />
      </div>
    );
  }
}

export default View;
