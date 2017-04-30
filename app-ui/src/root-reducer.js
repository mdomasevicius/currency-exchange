import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';
import currencyConversionState from './currency-conversion/currency-conversion-reducer';
import currencyPurchaseState from './currency-purchase/currency-purchase-reducer';

const rootReducer = combineReducers({
    currencyConversionState,
    currencyPurchaseState,
    routing: routerReducer,
});

export default rootReducer;
