import React, { Component } from 'react'

class HomePage extends Component {
  render() {
    return (
      <div>
        <div>Current page name: {this.props.pageName}</div>
        <div>This is Home page.</div>
        <div><a href="/edit-page">Go to Edit page</a></div>
      </div>
    )
  };
}

export default HomePage
