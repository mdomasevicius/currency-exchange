import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';
import currencyConversionState from './app/currency-conversion-reducer';

const rootReducer = combineReducers({
  currencyConversionState,
  routing: routerReducer,
});

export default rootReducer;
