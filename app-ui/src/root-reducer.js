import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';
import {reducer as formReducer} from 'redux-form';
import currencyConversionState from './app/currency-conversion-reducer';

const rootReducer = combineReducers({
  currencyConversionState,
  routing: routerReducer,
  form: formReducer
});

export default rootReducer;
