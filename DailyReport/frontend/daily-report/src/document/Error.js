import React, { Component } from 'react'

class Error extends Component {
  render() {
    return (
      <div>
        <button type="button" className="btn btn-outline-secondary ms-3 mb-2" onclick="window.location.href='/'">
          <a href="/" className="text-decoration-none">Home</a>
        </button>
        <h3 className="mt-3">404 Page not found!</h3>

      </div>
    )
  }
}

export default Error
