import React, {PropTypes} from "react";
import {Tab, Tabs} from "material-ui/Tabs";
import CurrencyConversionPage from "../currency-conversion/CurrencyConversionPage";
import CurrencyPurchasePage from "../currency-purchase/CurrencyPurchasePage";

const propTypes = {
    children: PropTypes.element
};

const styles = {
    headline: {
        fontSize: 24,
        paddingTop: 16,
        marginBottom: 12,
        fontWeight: 400,
    },
};

class HomePage extends React.Component {

    render() {
        return (
            <Tabs>
                <Tab label="Currency Conversion" >
                    <div>
                        <CurrencyConversionPage/>
                    </div>
                </Tab>
                <Tab label="Currency Purchase" >
                    <div>
                        <CurrencyPurchasePage/>
                    </div>
                </Tab>
            </Tabs>
        );
    }
}

HomePage.propTypes = propTypes;

export default HomePage;
