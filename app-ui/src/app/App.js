import React, {PropTypes} from "react";

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
