import React, {PropTypes} from "react";
/*eslint-disable no-unused-vars*/
//noinspection ES6UnusedImports
import * as style from "./App.css";
/*eslint-enable no-unused-vars*/

const propTypes = {
  children: PropTypes.element
};

class App extends React.Component {

  render() {
    return this.props.children;
  }
}

App.propTypes = propTypes;

export default App;
