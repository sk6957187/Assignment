import React, { Component } from 'react';
import axios from 'axios';

class CombineRule extends Component {
  constructor(props) {
    super(props);
    this.state = {
      combinedRuleName: '',
      combinedRuleValue: '',
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
      combinedRuleName: this.state.combinedRuleName,
      combinedRuleValue: this.state.combinedRuleValue
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
                <td><label>Combined Rule Name :</label></td>
                <td>
                  <input
                    type="text" name="combinedRuleName" value={this.state.combineRuleName1} onChange={this.handleChange} required
                  />
                </td>
                </tr>
                <tr>
                <td><label>Rule value:</label></td>
                <td>
                  <input
                    type="text" name="combinedRuleValue" value={this.state.combineRuleName2} onChange={this.handleChange} required
                  />
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
