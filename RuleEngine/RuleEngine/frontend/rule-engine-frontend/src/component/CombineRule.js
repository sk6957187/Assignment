import React, { Component } from 'react';
import axios from 'axios';

class CombineRule extends Component {
  constructor(props) {
    super(props);
    this.state = {
      combineRuleName1: '',
      combineRuleName2: '',
      combineRuleName3: 'OR', // Default value set to 'AND'
      errorMessage: '',
      successMessage: ''
    };
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      rule1: this.state.combineRuleName1,
      rule2: this.state.combineRuleName2,
      rule3: this.state.combineRuleName3 // This will be either 'AND' or 'OR'
    };

    axios.post('http://localhost:8080/api/rule-engine/combine', data)
      .then(response => {
        this.setState({ successMessage: 'Rules combined successfully!', errorMessage: '' });
      })
      .catch(error => {
        this.setState({ errorMessage: 'Failed to combine the rules. Please try again.', successMessage: '' });
      });
  };

  render() {
    return (
      <div>
        <h3>Combine Rules</h3>
        <form onSubmit={this.handleSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label>Rule 1:</label></td>
                <td>
                  <input
                    type="text" name="combineRuleName1" value={this.state.combineRuleName1} onChange={this.handleChange} required
                  />
                </td>
                <td><label>Rule 2:</label></td>
                <td>
                  <input
                    type="text" name="combineRuleName2" value={this.state.combineRuleName2} onChange={this.handleChange} required
                  />
                </td>
              </tr>
              <tr>
                <td><label>Operator:</label></td>
                <td>
                  <select
                    name="combineRuleName3" value={this.state.combineRuleName3}onChange={this.handleChange} required >
                    <option value="AND">AND</option>
                    <option value="OR">OR</option>
                  </select>
                </td>
              </tr>
            </tbody>
          </table>
          {this.state.errorMessage && (
            <span className="badge rounded-pill bg-danger">{this.state.errorMessage}</span>
          )}
          {this.state.successMessage && (
            <span className="badge rounded-pill bg-success">{this.state.successMessage}</span>
          )}
          <button type="submit">Submit</button>
        </form>
      </div>
    );
  }
}

export default CombineRule;
