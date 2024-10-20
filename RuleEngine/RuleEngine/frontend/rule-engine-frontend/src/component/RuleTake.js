import React, { Component } from 'react';
import axios from 'axios';

class RuleTake extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ruleName: '',
      ruleValue: '',
      errorMessage: ''
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
      this.setState({ errorMessage: 'Both Rule Name and Rule Value are required.' });
      return;
    }

    if (this.props.availableRules.includes(ruleName)) {
      this.setState({ errorMessage: 'Rule is available.' });
      return;
    }

    this.setState({ errorMessage: '' });

    const formData = {
      ruleName: ruleName,
      ruleValue: ruleValue
    };

    axios.post('http://localhost:8080/api/rule-take/submit', formData)
      .then(response => {
        alert('Rule submitted successfully!');
        this.props.updateRuleList(ruleName);
        this.setState({ ruleName: '', ruleValue: '' });
      })
      .catch(error => {
        alert('Error submitting rule: ' + error.message);
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
            </tbody></table>
          {this.state.errorMessage && (
            <p style={{ color: 'red' }}>{this.state.errorMessage}</p>
          )}
          <button type="submit">Submit</button>
        </form>
      </div>
    );
  }
}

export default RuleTake;
