import {types} from './currency-exchange-actions';
const defaultState = {
    convertedAmount: 0,
    requiredAmount: 0,
    commonCurrencies: [],
    baseCurrency: 'EUR',
    targetCurrency: 'USD',
    amount: 1,
    convertInProgress: true,
    purchaseInProgress: true,
    exchangeRateHistory: null
};

export default function currencyConversionStateReducer(state = defaultState, action) {
    switch (action.type) {
        case types.PURCHASE + '_PENDING':
            return {
                ...state,
                purchaseInProgress: true
            };
        case types.PURCHASE + '_FULFILLED':
            return {
                ...state,
                requiredAmount: action.payload.data.requiredAmount,
                purchaseInProgress: false
            };
        case types.PURCHASE + '_REJECTED':
            return {
                ...state,
                purchaseInProgress: false
            };
        case types.CONVERT_CURRENCY + '_PENDING':
            return {
                ...state,
                convertInProgress: true
            };
        case types.CONVERT_CURRENCY + '_FULFILLED':
            return {
                ...state,
                convertedAmount: action.payload.data.convertedAmount,
                convertInProgress: false
            };
        case types.CONVERT_CURRENCY + '_REJECTED':
            return {
                ...state,
                convertInProgress: false
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
            let exchangeRateHistory = {currencyCode: data.currencyCode, quotes: []};
            data.quotes.map((q) => {
                exchangeRateHistory.quotes.push({high: q.high, date: new Date(q.date)});
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
