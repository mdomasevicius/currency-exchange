import axios from 'axios';

export const types = {
  CONVERT_CURRENCY: 'CONVERT_CURRENCY',
  CONVERT_CURRENCY_FROM_STATE: 'CONVERT_CURRENCY_FROM_STATE',
  RETRIEVE_COMMON_CURRENCIES: 'RETRIEVE_COMMON_CURRENCIES',
  SET_BASE_CURRENCY: 'SET_BASE_CURRENCY',
  SET_TARGET_CURRENCY: 'SET_TARGET_CURRENCY',
  SET_AMOUNT: 'RETRIEVE_COMMON_CURRENCIES'
};

const convert = (baseCurrency, targetCurrency, amount) => ({
  type: types.CONVERT_CURRENCY,
  payload: {
    promise: axios.get('/api/exchange/conversion', {
      params: {
        baseCurrency: baseCurrency,
        targetCurrency: targetCurrency,
        amount: amount
      }
    })
  }
});


const retrieveCommonCurrencies = () => ({
  type: types.RETRIEVE_COMMON_CURRENCIES,
  payload: {
    promise: axios.get('/api/exchange/currencies')
  }
});

const changeBaseCurrency = (code) => ({
  type: types.SET_BASE_CURRENCY,
  payload: code
});

const changeTargetCurrency = (code) => ({
  type: types.SET_TARGET_CURRENCY,
  payload: code
});

const changeAmount = (amount) => ({
  type: types.SET_AMOUNT,
  payload: amount
});

export const actions = {
  convert: convert,
  retrieveCommonCurrencies: retrieveCommonCurrencies,
  changeBaseCurrency: changeBaseCurrency,
  changeTargetCurrency: changeTargetCurrency,
  changeAmount: changeAmount
};
