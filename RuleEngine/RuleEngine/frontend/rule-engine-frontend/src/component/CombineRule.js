import React,{Component} from 'react'

class CombineRule extends Component {
    constructor(props) {
        super(props);
        this.state = {
          ruleName: '',
          availableRules: this.props.availableRules
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
      }

      

    handleChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    };
    
  render(){
    return (
        <div>
            <h3>Combine Rule</h3>
        <form onSubmit={this.handleSubmit}>
          <table>
            <tbody>
              <tr>
                <td><label>Rules Name:</label></td>
                <td>
                  <input
                    type="text" name="combineRuleName"
                    value={this.state.combineRuleName}
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
  )};
}

export default CombineRule;
