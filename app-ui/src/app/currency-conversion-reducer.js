import {types} from './currency-conversion-actions';
const defaultState = {
  convertedAmount: 0,
  commonCurrencies: [],
  baseCurrency: 'USD',
  targetCurrency: 'EUR',
  amount: 1,
  convertingInProgress: true,
  exchangeRateHistory: null
};

export default function currencyConversionStateReducer(state = defaultState, action) {
  switch (action.type) {
    case types.CONVERT_CURRENCY + '_PENDING':
      return {
        ...state,
        convertingInProgress: true
      };
    case types.CONVERT_CURRENCY + '_FULFILLED':
      return {
        ...state,
        convertedAmount: action.payload.data.convertedAmount,
        convertingInProgress: false
      };
    case types.CONVERT_CURRENCY + '_REJECTED':
      return {
        ...state,
        convertingInProgress: false
      };

    case types.RETRIEVE_COMMON_CURRENCIES + '_FULFILLED':
      return {
        ...state,
        commonCurrencies: action.payload.data
      };

    case types.RETRIEVE_CURRENCY_HISTORY + '_PENDING':
      return {
        ...state,
        exchangeRateHistory: null
      };
    case types.RETRIEVE_CURRENCY_HISTORY + '_FULFILLED': {
      let data = action.payload.data;
      let exchangeRateHistory = { currencyCode: data.currencyCode, quotes: [] };
      data.quotes.map((q) => {
        exchangeRateHistory.quotes.push({ high: q.high, date: new Date(q.date)});
      });

      return {
        ...state,
        exchangeRateHistory: exchangeRateHistory
      };
    }
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
