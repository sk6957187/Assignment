import React, { Component } from 'react';
import axios from 'axios';


class RuleForm extends Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange = (e) => {
        var name = e.target.name;
        var currentData = {};
        currentData[name] = e.target.value;
        this.setState(currentData);
    };
    handleSubmit = (e) => {
        e.preventDefault();
        var formData = {};
        formData["rule_name"] = this.state.ruleName;
        formData["age"] = this.state.age;
        formData["department"] = this.state.department;
        formData["salary"] = this.state.salary;
        formData["experience"] = this.state.experience;
        
        axios.post('http://localhost:8080/api/rule-engine/evaluate', formData)
          .then(response => {
            var responseClassName = "badge rounded-pill";
            var responseData = "Response data: ";
            if (response) {
                if (response.data.status == "SUCCESS") {
                    if (response.data.data) {
                        responseClassName += " bg-success";
                    } else {
                        responseClassName += " bg-danger";
                    }
                    responseData += response.data.data;
                } else {
                    responseData += response.data.error;
                    responseClassName += " bg-danger";
                }
            } else {
                responseData += "null";
                responseClassName += " bg-danger";
            }
            this.setState({"responseData": responseData, "responseClass": responseClassName});
          })
          .catch(response => {
            this.setState({"responseData": "Response data: "+ response.response.data, "responseClass": "badge rounded-pill bg-danger"});
            // console.error('There was an error!', error);
          });
    };
    render ()  {
        return (<div>
          <center><h2>Submit Rule for Processing</h2></center>
          <form onSubmit={this.handleSubmit}>
            <table className="table"><tbody>
                <tr>
                    <td><label>Rule name:</label></td>
                    <td>
                        <select name="ruleName" onChange={this.handleChange}>
                            <option value="">Select rule name ...</option>
                            <option value="rule1">Rule1</option>
                            <option value="rule2">Rule2</option>
                            <option value="cartoon">Cartoon</option>
                            <option value="rule4">Rule4</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Age:</label></td>
                    <td>
                        <div>
                            <input
                                type="text"
                                name="age"
                                onChange={this.handleChange}
                                />
                        </div>
                    </td>
                    <td><label>Department:</label></td>
                    <td>
                        <div>
                            <input
                                type="text"
                                name="department"
                                onChange={this.handleChange}
                            />
                        </div>
                    </td>
                </tr>
                <tr>
                <td><label>Salary:</label></td>
                    <td>
                        <div>
                            <input
                                type="text"
                                name="salary"
                                onChange={this.handleChange}
                            />
                        </div>
                    </td>
                    <td><label>Experience:</label></td>
                    <td>
                        <div>
                            <input
                                type="text"
                                name="experience"
                                onChange={this.handleChange}
                            />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="submit">Submit</button></td>
                </tr>
                <tr>
                    <td></td>
                    {/* <button type="button" class="btn btn-primary">Notifications <span class="badge bg-secondary">4</span></button> */}
                    {/* this.state.responseData === true */}
                    <td>
                        <span className={this.state.responseClass}>{this.state.responseData}</span>
                    </td>

                </tr>
            </tbody></table>
          </form>
        </div>
      );
    }
}

export default RuleForm;
