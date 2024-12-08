import React, { Component } from 'react'

class Error extends Component {
  render() {
    return (
      <div>
        <div>Requested page does not exist</div>
        <div><a href="/edit-page">Go to Edit page</a>|<a href="/">Go to Home page</a></div>
      </div>
    )
  };
}

export default Error
