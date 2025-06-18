import React from "react";
import { BrowserRouter, Route, Switch, Link } from "react-router-dom"; // Added Link to imports
import HomePage from "./HomePage";
import EditPage from "./EditPage";
import Error from "./Error";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <h2 className="text-center">Welcome To Browser Router!</h2>
        <Switch>
          <Route exact path='/'
            render={props => (
                <HomePage pageName="Home"/>
            )}
          />
          <Route path='/edit-Page' component={EditPage} />
          <Route path="*" component={Error} /> {/* Fallback for unknown URLs */}
        </Switch>

      </div>
    </BrowserRouter>
  );
}

export default App;
