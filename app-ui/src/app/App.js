import React, {PropTypes} from "react";

const propTypes = {
    children: PropTypes.element
};

class App extends React.Component {

    render() {
        return (
            <div>
                <div className="content-wrapper">
                    {this.props.children}
                </div>
            </div>
        );
    }
}

App.propTypes = propTypes;

export default App;
