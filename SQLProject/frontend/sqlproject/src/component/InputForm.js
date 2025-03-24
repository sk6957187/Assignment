import React, { Component, createRef  } from "react";

export class InputForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      age: "",
      dob: "",
      image: null,
      video: null,
      audio: null,
      textFile: null,
      address: "",
      submittedData: [],
    };
     // Refs for file inputs
     this.imageRef = createRef();
     this.videoRef = createRef();
     this.audioRef = createRef();
     this.textFileRef = createRef();
  }

  handleChange = (e) => {
    const { name, type, files, value } = e.target;
    if (type === "file" && files[0]) {
      const reader = new FileReader();
      reader.readAsDataURL(files[0]);
      reader.onload = () => {
        this.setState({ [name]: reader.result });
      };
    } else {
      this.setState({ [name]: value });
    }
  };
  


  uploadData = async (newData) => {
    try {
      console.log("Added data: ", newData);
      const response = await fetch("http://localhost:8080/sql/add-data", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify([newData]), // Sending an array
      });
  
      if (response.ok) {
        var message = await response.text();
        alert(message);
      }
    } catch (error) {
      console.error("Error adding row:", error);
      alert("Error adding row: " + error.message);
    }
  };
  

  handleSubmit = (e) => {
    e.preventDefault();
    
    const newData = {
      name: this.state.name,
      age: this.state.age,
      dob: this.state.dob,
      image: this.state.image,
      video: this.state.video,
      audio: this.state.audio,
      textFile: this.state.textFile ? this.state.textFile.name : "No file",
      address: this.state.address,
    };
       

    this.setState((prevState) => ({
      submittedData: [...prevState.submittedData, newData],
      name: "",
      age: "",
      dob: "",
      image: null,
      video: null,
      audio: null,  
      textFile: null,
      address: "",
    }));

    this.uploadData(newData);

    if (this.imageRef.current)
       this.imageRef.current.value = "";
    if (this.videoRef.current)
       this.videoRef.current.value = "";
    if (this.audioRef.current)
       this.audioRef.current.value = "";
    if (this.textFileRef.current)
       this.textFileRef.current.value = "";
  };

  render() {
    return (
      <div className="container">
        <h2 className="title">Input Form Page</h2>
        <form onSubmit={this.handleSubmit} className="form">
          <label>Name:</label>
          <input type="text" required name="name" value={this.state.name} onChange={this.handleChange} />

          <label>Age:</label>
          <input type="number" name="age" value={this.state.age} onChange={this.handleChange} />

          <label>DOB:</label>
          <input type="date" name="dob" value={this.state.dob} onChange={this.handleChange} />

          <label>Image:</label>
          <input type="file" name="image" accept="image/*" onChange={this.handleChange} />

          <label>Video:</label>
          <input type="file" name="video" accept="video/*" onChange={this.handleChange} />

          <label>Audio:</label>
          <input type="file" name="audio" accept="audio/*" onChange={this.handleChange} />

          <label>Text File:</label>
          <input type="file" name="textFile" accept=".txt" onChange={this.handleChange} />

          <label>Address:</label>
          <textarea name="address" value={this.state.address} onChange={this.handleChange}></textarea>

          <button type="submit" className="submit-btn">Submit</button>
        </form>

        {/* Display Submitted Data in a Table */}
        {this.state.submittedData.length > 0 && (
          <div className="table-container">
            <h3>Submitted Data</h3>
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Age</th>
                  <th>DOB</th>
                  <th>Image</th>
                  <th>Video</th>
                  <th>Audio</th>
                  <th>Text File</th>
                  <th>Address</th>
                </tr>
              </thead>
              <tbody>
                {this.state.submittedData.map((data, index) => (
                  <tr key={index}>
                    <td>{data.name}</td>
                    <td>{data.age}</td>
                    <td>{data.dob}</td>
                    <td>{data.image ? <img src={data.image} alt="Preview" width="50" /> : "No Image"}</td>
                    <td>{data.video ? <a href={data.video} target="_blank" rel="noopener noreferrer">View Video</a> : "No Video"}</td>
                    <td>{data.audio ? <audio controls src={data.audio}></audio> : "No Audio"}</td>
                    <td>{data.textFile}</td>
                    <td>{data.address}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {/* CSS Styling */}
        <style jsx>{`
          .container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
          }
          .title {
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
          }
          .form {
            display: flex;
            flex-direction: column;
            gap: 10px;
          }
          label {
            font-weight: bold;
          }
          input, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
          }
          .submit-btn {
            padding: 10px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
          }
          .submit-btn:hover {
            background: #218838;
          }
          .table-container {
            margin-top: 20px;
          }
          table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
          }
          th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
          }
          th {
            background-color: #f4f4f4;
          }
        `}</style>
      </div>
    );
  }
}

export default InputForm;
