import axios from 'axios';

export const types = {
  CONVERT_CURRENCY: 'CONVERT_CURRENCY'
};

const convert = (baseCurrency, targetCurrency, amount) => ({
  type: types.CONVERT_CURRENCY,
  payload: {
    promise: axios.get('/api/currency/conversion', {
      params: {
        baseCurrency: baseCurrency,
        targetCurrency: targetCurrency,
        amount: amount
      }
    })
  }
});

export const actions = {
  convert: convert
};
