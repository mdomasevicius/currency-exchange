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

const changeBaseCurrencyAndRetrieveHistory = (currencyCode) => (dispatch) => {
    dispatch(changeBaseCurrency(currencyCode));
    dispatch(retrieveCurrencyHistory(currencyCode));
};

const changeBaseCurrency = (currencyCode) => ({
    type: types.SET_BASE_CURRENCY,
    payload: currencyCode
});


const changeTargetCurrency = (currencyCode) => ({
    type: types.SET_TARGET_CURRENCY,
    payload: currencyCode
});

const changeAmount = (amount) => ({
    type: types.SET_AMOUNT,
    payload: amount
});

export const actions = {
    convert: convert,
    changeAmount: changeAmount,
    retrieveCommonCurrencies: retrieveCommonCurrencies,
    changeBaseCurrency: changeBaseCurrency,
    changeTargetCurrency: changeTargetCurrency,
    retrieveCurrencyHistory: retrieveCurrencyHistory,
    changeBaseCurrencyAndRetrieveHistory: changeBaseCurrencyAndRetrieveHistory
};
