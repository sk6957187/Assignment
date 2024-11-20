import React, { Component } from 'react';
import axios from 'axios';
import RuleTake from './RuleTake';
import CombineRule from './CombineRule';

class RuleForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ruleName: '',
      availableRules: ['Rule1', 'Rule2', 'Rule3', 'cartoon', 'Rule4'],
      showRuleTake: false,
      showCombineRule: false
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.toggleRuleTake = this.toggleRuleTake.bind(this);
    this.updateRuleList = this.updateRuleList.bind(this);
    this.toggleCombineRule = this.toggleCombineRule.bind(this);
    this.baseApi = window.Global.baseApi;
    this.staticDataApi = `${this.baseApi}/api/rule-engine/base-api`;
  }

  handleChange = (e) => {
    const name = e.target.name;
    const currentData = {};
    currentData[name] = e.target.value;
    this.setState(currentData);
  };

  handleSubmit = (e) => {
    e.preventDefault();
    const formData = {
      rule_name: this.state.ruleName,
      age: this.state.age,
      department: this.state.department,
      salary: this.state.salary,
      experience: this.state.experience
    };

    axios.post(`${this.baseApi}/api/rule-engine/evaluate`, formData)
      .then(response => {
        let responseClassName = "badge rounded-pill";
        let responseData = "Response data: ";
        if (response) {
          if (response.data.status === "SUCCESS") {
            responseClassName += response.data.data ? " bg-success" : " bg-danger";
            responseData += response.data.data;
          } else {
            responseClassName += " bg-danger";
            responseData += response.data.error;
          }
        } else {
          responseClassName += " bg-danger";
          responseData += "null";
        }
        this.setState({ responseData, responseClass: responseClassName });
      })
      .catch(error => {
        this.setState({ responseData: `Response data: ${error.response.data}`, responseClass: "badge rounded-pill bg-danger" });
      });
  };

  toggleRuleTake() {
    this.setState({ showRuleTake: !this.state.showRuleTake });
  };

  toggleCombineRule() {
    this.setState({ showCombineRule: !this.state.showCombineRule});
  };

  updateRuleList(newRule) {
    this.setState(prevState => ({
      availableRules: [...prevState.availableRules, newRule]
    }));
  }

  render() {
    return (
      <div>
        <center><h2>Submit Rule for Processing</h2></center>
        <form onSubmit={this.handleSubmit}>
          <table className="table">
            <tbody>
              <tr>
                <td><label>Rule name:</label></td>
                <td>
                  <select name="ruleName" onChange={this.handleChange}>
                    <option value="">Select rule name ...</option>
                    {this.state.availableRules.map(rule => (
                      <option key={rule} value={rule}>{rule}</option>
                    ))}
                  </select>
                </td>
              </tr>
              <tr>
                <td><label>Age:</label></td>
                <td><input type="text" name="age" onChange={this.handleChange} /></td>
                <td><label>Department:</label></td>
                <td><input type="text" name="department" onChange={this.handleChange} /></td>
              </tr>
              <tr>
                <td><label>Salary:</label></td>
                <td><input type="text" name="salary" onChange={this.handleChange} /></td>
                <td><label>Experience:</label></td>
                <td><input type="text" name="experience" onChange={this.handleChange} /></td>
              </tr>
              <tr>
                <td></td>
                <td><button type="submit">Submit</button></td>
              </tr>
              <tr>
                <td></td>
                <td><span className={this.state.responseClass}>{this.state.responseData}</span></td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <button className="btn btn-secondary mx-1" type="button" onClick={this.toggleRuleTake}>Open Create Rule</button>
                  <button className ="btn btn-secondary mx-1" type="button" onClick={this.toggleCombineRule}>Open Combine Rule</button>
                </td>
              </tr>
            </tbody></table>
        </form>
        {this.state.showRuleTake && (
          <RuleTake
            availableRules={this.state.availableRules}
            updateRuleList={this.updateRuleList}
          />
        )}
        {this.state.showCombineRule && (
          <CombineRule
          availableRules={this.state.availableRules}
          />
        )}
      </div>
    );
  }
}

export default RuleForm;