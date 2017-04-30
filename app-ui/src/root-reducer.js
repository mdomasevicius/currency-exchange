import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';
import currencyConversionState from './currency-conversion/currency-conversion-reducer';

const rootReducer = combineReducers({
    currencyConversionState,
    routing: routerReducer,
});

export default rootReducer;
