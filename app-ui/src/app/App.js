import React, {PropTypes} from "react";
import AppBar from 'material-ui/AppBar';

const propTypes = {
    children: PropTypes.element
};

class App extends React.Component {

    render() {
        return (
            <div>
                <AppBar
                    title="Currency Exchange"
                />
                <div>
                    {this.props.children}
                </div>
            </div>
        );
    }
}

App.propTypes = propTypes;

export default App;
