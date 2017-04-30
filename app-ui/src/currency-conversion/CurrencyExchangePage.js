import React, {PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {actions} from './currency-exchange-actions';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import TextField from 'material-ui/TextField';
import Divider from 'material-ui/Divider';
import LinearProgress from 'material-ui/LinearProgress';
import HistoricalDataChart from '../extra/HistoricalDataChart';

const propTypes = {
    actions: PropTypes.objectOf(PropTypes.func.isRequired).isRequired,
    currencyConversionState: PropTypes.object.isRequired
};

class CurrencyExchangePage extends React.Component {

    constructor(props) {
        super(props);
        this.handleBaseCurrencyChange = this.handleBaseCurrencyChange.bind(this);
        this.handleTargetCurrencyChange = this.handleTargetCurrencyChange.bind(this);
        this.handleAmountChange = this.handleAmountChange.bind(this);
    }

    componentWillMount() {
        const {baseCurrency} = this.props.currencyConversionState;

        this.props.actions.retrieveCommonCurrencies();
        this.props.actions.convertFromState();
        this.props.actions.purchaseFromState();
        this.props.actions.retrieveCurrencyHistory(baseCurrency);
    }

    handleBaseCurrencyChange(event, index, value) {
        this.props.actions.changeBaseCurrency(value);
        this.props.actions.retrieveCurrencyHistory(value);
        this.props.actions.convertFromState();
        this.props.actions.purchaseFromState();
    }

    handleTargetCurrencyChange(event, index, value) {
        this.props.actions.changeTargetCurrency(value);
        this.props.actions.convertFromState();
        this.props.actions.purchaseFromState();
    }

    handleAmountChange(event, newValue) {
        this.props.actions.changeAmount(newValue);
        this.props.actions.convertFromState();
        this.props.actions.purchaseFromState();
    }

    render() {
        const {
            convertedAmount,
            requiredAmount,
            commonCurrencies,
            targetCurrency,
            baseCurrency,
            amount,
            convertInProgress,
            purchaseInProgress,
            exchangeRateHistory
        } = this.props.currencyConversionState;

        const mappedCurrencyCodes = commonCurrencies.map((c) => {
            return <MenuItem key={c.code} value={c.code} primaryText={c.code}/>;
        });

        return (
            <div >
                <div style={{height: '10vh'}}>
                    <span style={convertInProgress || purchaseInProgress ? {} : {display: 'none'}}>
                        <LinearProgress mode="indeterminate"/>
                    </span>
                </div>

                <div style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    flexFlow: 'column'
                }}>
                    <div>
                        <h1>Currency Exchange</h1>
                    </div>

                    <div>
                        <TextField
                            floatingLabelText="Amount"
                            id="amount"
                            hintText="Amount"
                            onChange={this.handleAmountChange}
                            value={amount}/>

                        <DropDownMenu
                            onChange={this.handleBaseCurrencyChange}
                            value={baseCurrency}>
                            {mappedCurrencyCodes}
                        </DropDownMenu>
                    </div>

                    <div>
                        <TextField
                            floatingLabelText="Converted Amount"
                            id="converted_amount"
                            disabled={true}
                            value={convertedAmount}/>

                        <DropDownMenu
                            onChange={this.handleTargetCurrencyChange}
                            value={targetCurrency}>
                            {mappedCurrencyCodes}
                        </DropDownMenu>

                        <TextField
                            floatingLabelText="Required Amount"
                            id="required_amount"
                            disabled={true}
                            value={requiredAmount}/>
                    </div>
                </div>

                <div style={{height: '10vh'}}/>
                <div>
                    <Divider/>
                </div>

                <div>
                    <HistoricalDataChart
                        currentAmount={amount}
                        exchangeRateHistory={exchangeRateHistory}/>
                </div>
            </div>
        );
    }
}

CurrencyExchangePage.propTypes = propTypes;

const mapStateToProps = (state) => ({
    currencyConversionState: state.currencyConversionState
});

const mapDispatchToProps = (dispatch) => ({
    actions: bindActionCreators(actions, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyExchangePage);
