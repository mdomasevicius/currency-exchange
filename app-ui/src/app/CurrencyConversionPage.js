import React, {PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {actions} from './currency-conversion-actions';

const defaultProps = {
  name: ''
};

const propTypes = {
  actions: PropTypes.objectOf(PropTypes.func.isRequired).isRequired,
  currencyConversionState: PropTypes.object.isRequired
};

class CurrencyConversionPage extends React.Component {

  render() {
    const {convertedAmount} = this.props.currencyConversionState;
    return (
      <div>
        <h1>Welcome!</h1>
        <button onClick={() => {
          this.props.actions.convert('USD', 'EUR', 100);
        }}>Click
        </button>
        <div>{convertedAmount}</div>
      </div>
    );
  }
}

CurrencyConversionPage.defaultProps = defaultProps;
CurrencyConversionPage.propTypes = propTypes;


const mapStateToProps = (state) => ({
  currencyConversionState: state.currencyConversionState
});

const mapDispatchToProps = (dispatch) => ({
  actions: bindActionCreators(actions, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyConversionPage);
