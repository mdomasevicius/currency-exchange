import axios from 'axios';

export const types = {
    CONVERT_CURRENCY: 'CONVERT_CURRENCY',
    CONVERT_CURRENCY_FROM_STATE: 'CONVERT_CURRENCY_FROM_STATE',
    RETRIEVE_COMMON_CURRENCIES: 'RETRIEVE_COMMON_CURRENCIES',
    SET_BASE_CURRENCY: 'SET_BASE_CURRENCY',
    SET_TARGET_CURRENCY: 'SET_TARGET_CURRENCY',
    SET_AMOUNT: 'RETRIEVE_COMMON_CURRENCIES',
    RETRIEVE_CURRENCY_HISTORY: 'RETRIEVE_CURRENCY_HISTORY'
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

const retrieveCurrencyHistory = (currencyCode) => ({
    type: types.RETRIEVE_CURRENCY_HISTORY,
    payload: {
        promise: axios.get('/api/exchange/history', {
            params: {
                currencyCode: currencyCode
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

const changeBaseCurrencyAndConvert = (baseCurrency) => (dispatch, getState) => {
    const {targetCurrency, amount} = getState().currencyConversionState;
    dispatch(changeBaseCurrency(baseCurrency));
    dispatch(convert(baseCurrency, targetCurrency, amount));
};
const changeBaseCurrency = (currencyCode) => ({
    type: types.SET_BASE_CURRENCY,
    payload: currencyCode
});

const changeTargetCurrencyAndConvert = (targetCurrency) => (dispatch, getState) => {
    const {baseCurrency, amount} = getState().currencyConversionState;
    dispatch(changeTargetCurrency(targetCurrency));
    dispatch(convert(baseCurrency, targetCurrency, amount));
};
const changeTargetCurrency = (currencyCode) => ({
    type: types.SET_TARGET_CURRENCY,
    payload: currencyCode
});

const changeAmountAndConvert = (amount) => (dispatch, getState) => {
    const {baseCurrency, targetCurrency} = getState().currencyConversionState;
    dispatch(changeAmount(amount));
    dispatch(convert(baseCurrency, targetCurrency, amount));
};
const changeAmount = (amount) => ({
    type: types.SET_AMOUNT,
    payload: amount
});

export const actions = {
    convert: convert,
    changeAmountAndConvert: changeAmountAndConvert,
    retrieveCommonCurrencies: retrieveCommonCurrencies,
    changeBaseCurrencyAndConvert: changeBaseCurrencyAndConvert,
    changeTargetCurrencyAndConvert: changeTargetCurrencyAndConvert,
    retrieveCurrencyHistory: retrieveCurrencyHistory,
};
