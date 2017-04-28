import {types} from './currency-conversion-actions';
const defaultState = {
  convertedAmount: 0
};

export default function currencyConversionStateReducer(state = defaultState, action) {
  switch (action.type) {
    case types.CONVERT_CURRENCY + '_FULFILLED':
      return {
        ...state,
        convertedAmount: action.payload.data.convertedAmount
      };
    default:
      return state;
  }
}
