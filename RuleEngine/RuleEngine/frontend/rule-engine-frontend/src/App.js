import logo from './logo.svg';
import React, { Component } from "react";
import './App.css';
import RuleForm from './component/RuleForm';
import RuleTake from './component/RuleTake';




class App extends Component {
  render() {
    return (
      <>
        {/* <RuleTake /> */}
        <RuleForm />
      </>
    );
  }
}
export default App;
