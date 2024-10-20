import React, { Component } from 'react';
import axios from 'axios';

class RuleTake extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ruleName: '',
      ruleValue: '',
      errorMessage: '',
      responseMessage: ''  // New state for response message
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { ruleName, ruleValue } = this.state;

    if (!ruleName || !ruleValue) {
      this.setState({ errorMessage: 'Both Rule Name and Rule Value are required.', responseMessage: '' });
      return;
    }

    if (this.props.availableRules.includes(ruleName)) {
      this.setState({ errorMessage: 'Rule is available.', responseMessage: '' });
      return;
    }

    this.setState({ errorMessage: '', responseMessage: '' });

    const formData = {
      ruleName: ruleName,
      ruleValue: ruleValue
    };

    axios.post('http://localhost:8080/api/rule-engine/create-rule', formData)
      .then(response => {
        // Set response message on successful submission
        this.setState({
          responseMessage: 'Rule submitted successfully!',
          ruleName: '',
          ruleValue: ''
        });
        this.props.updateRuleList(ruleName);
      })
      .catch(error => {
        // Set response message on error
        this.setState({ responseMessage: 'Error submitting rule: ' + error.message });
      });
  };

  render() {
    return (
      <div>
        <h3>Create Rule</h3>
        <form onSubmit={this.handleSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label>Rule Name:</label></td>
                <td>
                  <input
                    type="text" name="ruleName"
                    value={this.state.ruleName}
                    onChange={this.handleChange}
                    required
                  />
                </td>
              </tr>
              <tr>
                <td><label>Rule Value:</label></td>
                <td>
                  <input
                    type="text" name="ruleValue"
                    value={this.state.ruleValue}
                    onChange={this.handleChange}
                    required
                  />
                </td>
              </tr>
            </tbody>
          </table>
          {this.state.errorMessage && (
            <p className="badge rounded-pill bg-danger" >{this.state.errorMessage}</p>
          )}
          <button type="submit">Submit</button>
          {/* Display response message below the button */}
          {this.state.responseMessage && (
            <p style={{ color: this.state.responseMessage.startsWith('Error') ? 'red' : 'green' }}>
              {this.state.responseMessage}
            </p>
          )}
          {this.state.responseMessage && (
            <p className={this.state.responseMessage.startsWith('Error') ? "badge rounded-pill bg-danger" : "badge rounded-pill bg-success"}>
              {this.state.responseMessage}
            </p>
          )}
        </form>
      </div>
    );
  }
}

export default RuleTake;