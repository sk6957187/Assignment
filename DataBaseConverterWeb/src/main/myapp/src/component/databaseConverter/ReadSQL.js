import React, { useState } from 'react';
import Result from './Result';

const ReadSQL = () => {
  const [database, setDatabase] = useState("");
  const [query, setQuery] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  const fetchData = async () => {
    if (query.includes(';')) {
      setError("Semicolons (;) are not allowed in queries.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/databaseconvert/readmysql", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ query, database }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Response:", data);
        setResult(data);
        setError('');
      } else {
        throw new Error("Failed to fetch data");
      }
    } catch (error) {
      console.error("Error:", error.message);
      setError(error.message);
      setResult(null);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Submitted query:", query);
    fetchData();
  };

  const renderForm = () => (
    <form onSubmit={handleSubmit}>
      <div className="mt-4 mb-3">
        <label htmlFor="database" className="form-label">
          Select Database:
        </label>
        <select
          className="form-select w-auto"
          id="database"
          value={database}
          onChange={(e) => setDatabase(e.target.value)}
          required
        >
          <option value="">-- Select --</option>
          <option value="mysql">MySQL</option>
          <option value="oraclesql">Oracle SQL</option>
          <option value="postgresql">Postgre SQL</option>
        </select>
      </div>

      <div className="mb-3">
        <label htmlFor="query" className="form-label">Query:</label>
        <input
          type="text"
          className="form-control"
          id="query"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Enter your SQL query"
          required
        />
        <small className="text-danger">* Cannot use <b>;</b> in query</small>
      </div>

      <div className="text-center">
        <button type="submit" className="btn btn-primary">Submit</button>
      </div>

      {error && (
        <div className="alert alert-danger mt-3" role="alert">
          {error}
        </div>
      )}
    </form>
  );


  const renderResult = () => {
    if (result?.error) {
      return (
        <div className="alert alert-danger">{result.error}</div>
      );
    } else if (result?.data?.length === 0) {
      return (
        <>
          <div className="d-flex justify-content-end mb-2">
            <button className="btn btn-secondary" onClick={() => setResult(null)}>
              Search Again
            </button>
          </div>    
          <div className="alert alert-info">No data found for the given query.</div>
        </>
      );
    } else {
      return (
        <div className="mt-4">
          <h5>Result Data:</h5>
          <div className="d-flex justify-content-end mb-2">
            <button className="btn btn-secondary" onClick={() => setResult(null)}>
              Search Again
            </button>
          </div>
          <Result data={result.data} />
        </div>
      );
    }
  };


  return (
    <div className="container mt-4">
      <h3>Read SQL Page</h3>
      {result ? renderResult() : renderForm()}
    </div>
  );
};

export default ReadSQL;
