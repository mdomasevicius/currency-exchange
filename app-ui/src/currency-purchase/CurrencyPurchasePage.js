import React, {PropTypes} from "react";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {actions} from "./currency-purchase-actions";
import DropDownMenu from "material-ui/DropDownMenu";
import TextField from "material-ui/TextField";

const propTypes = {
};

class CurrencyPurchasePage extends React.Component {
    render() {
        return (
            <div >
                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    flexFlow: 'column'
                }}>
                    <div>
                        <TextField
                            floatingLabelText="Amount to purchase"
                            id="amount"
                            hintText="Amount to purchase"
                            />

                        <DropDownMenu >
                        </DropDownMenu>
                    </div>

                    <div>
                        <TextField
                            floatingLabelText="Required amount"
                            id="required_amount"
                            disabled={true}
                            />

                        <DropDownMenu>
                        </DropDownMenu>
                    </div>
                </div>
            </div>
        );
    }
}

CurrencyPurchasePage.propTypes = propTypes;

const mapStateToProps = (state) => ({
    currencyPurchaseState: state.currencyPurchaseState
});

const mapDispatchToProps = (dispatch) => ({
    actions: bindActionCreators(actions, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyPurchasePage);
