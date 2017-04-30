import React, {PropTypes} from "react";
import CurrencyConversionPage from "../currency-conversion/CurrencyExchangePage";

const propTypes = {
    children: PropTypes.element
};

class HomePage extends React.Component {

    render() {
        return (
            <CurrencyConversionPage/>
        );
    }
}

HomePage.propTypes = propTypes;

export default HomePage;
