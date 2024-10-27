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

    // Validate if both ruleName and ruleValue are provided
    if (!ruleName || !ruleValue) {
      this.setState({
        errorMessage: 'Both Rule Name and Rule Value are required.',
        responseMessage: ''
      });
      return;
    }

    // Check if rule is already available
    if (this.props.availableRules.includes(ruleName)) {
      this.setState({
        errorMessage: 'Rule is already available.',
        responseMessage: ''
      });
      return;
    }

    // Clear error and response messages before submission
    this.setState({ errorMessage: '', responseMessage: '' });

    const formData = {
      ruleName: ruleName,
      ruleValue: ruleValue
    };

    // Post form data to the backend
    axios.post('http://localhost:8080/api/rule-engine/create-rule', formData)
      .then(response => {
        // Update response message upon successful submission
        this.setState({
          responseMessage: 'Rule created successfully!',  // Updated success message
          ruleName: '',
          ruleValue: '',
          errorMessage: ''
        });
        console.log("Response:", response.data);

        // Update parent component's rule list with the new rule
        this.props.updateRuleList(ruleName);
      })
      .catch(error => {
        // Handle error during submission
        this.setState({
          errorMessage: 'Error submitting rule. Please try again: ' + error.message,
          responseMessage: ''
        });
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
                    type="text"
                    name="ruleName"
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
                    type="text"
                    name="ruleValue"
                    value={this.state.ruleValue}
                    onChange={this.handleChange}
                    required
                  />
                </td>
              </tr>
            </tbody>
          </table>

          {/* Display error message if exists */}
          {this.state.errorMessage && (
            <p className="badge rounded-pill bg-danger">{this.state.errorMessage}</p>
          )}

          <button type="submit">Submit</button>

          {/* Display response message after submission */}
          <div>
            {this.state.responseMessage && (
              <p className="badge rounded-pill bg-success">{this.state.responseMessage}</p>
            )}
          </div>
        </form>
      </div>
    );
  }
}

export default RuleTake;
