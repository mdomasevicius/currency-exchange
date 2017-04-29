import React, {PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {actions} from './currency-conversion-actions';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

const propTypes = {
  actions: PropTypes.objectOf(PropTypes.func.isRequired).isRequired,
  currencyConversionState: PropTypes.object.isRequired
};

class CurrencyConversionPage extends React.Component {

  constructor(props) {
    super(props);
    this.handleBaseCurrencyChange = this.handleBaseCurrencyChange.bind(this);
    this.handleTargetCurrencyChange = this.handleTargetCurrencyChange.bind(this);
    this.handleAmountChange = this.handleAmountChange.bind(this);
  }

  componentWillMount() {
    const {
      targetCurrency,
      baseCurrency,
      amount
    } = this.props.currencyConversionState;

    this.props.actions.retrieveCommonCurrencies();
    this.props.actions.convert(baseCurrency, targetCurrency, amount);
  }

  handleBaseCurrencyChange(event, index, value) {
    this.props.actions.changeBaseCurrency(value);
  }

  handleTargetCurrencyChange(event, index, value) {
    this.props.actions.changeTargetCurrency(value);
  }

  handleAmountChange(event, newValue) {
    this.props.actions.changeAmount(newValue);
  }

  render() {
    const {
      convertedAmount,
      commonCurrencies,
      targetCurrency,
      baseCurrency,
      amount
    } = this.props.currencyConversionState;

    const mappedCurrencyCodes = commonCurrencies.map((c) => {
      return <MenuItem key={c.code} value={c.code} primaryText={c.code}/>;
    });

    return (
      <div>
        <div>
          <TextField
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
            disabled={true}
            value={convertedAmount}/>

          <DropDownMenu
            onChange={this.handleTargetCurrencyChange}
            value={targetCurrency}>
            {mappedCurrencyCodes}
          </DropDownMenu>
        </div>
        <div>
          <RaisedButton label="Convert" primary={true} onTouchTap={() => {
            this.props.actions.convert(baseCurrency, targetCurrency, amount);
          }}/>
        </div>
      </div>
    );
  }
}

CurrencyConversionPage.propTypes = propTypes;


const mapStateToProps = (state) => ({
  currencyConversionState: state.currencyConversionState
});

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators(actions, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyConversionPage);
