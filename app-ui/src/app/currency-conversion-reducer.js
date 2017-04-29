import {types} from './currency-conversion-actions';
const defaultState = {
  convertedAmount: 0,
  commonCurrencies: [],
  baseCurrency: 'USD',
  targetCurrency: 'EUR',
  amount: 1
};

export default function currencyConversionStateReducer(state = defaultState, action) {
  switch (action.type) {
    case types.CONVERT_CURRENCY + '_FULFILLED':
      return {
        ...state,
        convertedAmount: action.payload.data.convertedAmount
      };

    case types.RETRIEVE_COMMON_CURRENCIES + '_FULFILLED':
      return {
        ...state,
        commonCurrencies: action.payload.data
      };

    case types.SET_BASE_CURRENCY:
      return {
        ...state,
        baseCurrency: action.payload
      };

    case types.SET_TARGET_CURRENCY:
      return {
        ...state,
        targetCurrency: action.payload
      };

    case types.SET_AMOUNT:
      return {
        ...state,
        amount: action.payload
      };
    default:
      return state;
  }
}
