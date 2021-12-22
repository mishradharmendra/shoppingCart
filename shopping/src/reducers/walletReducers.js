import {
  WALLET_LIST_REQUEST,
  WALLET_LIST_SUCCESS,
  WALLET_LIST_FAIL,
  WALLET_DETAILS_REQUEST,
  WALLET_DETAILS_SUCCESS,
  WALLET_DETAILS_FAIL,
} from '../constants/walletConstants'

export const customerWalletReducer = (
  state = { loading: true },
  action
) => {
  switch (action.type) {
    case WALLET_DETAILS_REQUEST:
      return {
        ...state,
        loading: true,
      }
    case WALLET_DETAILS_SUCCESS:
      return {
        loading: false,
        wallet: action.payload,
      }
    case WALLET_DETAILS_FAIL:
      return {
        loading: false,
        error: action.payload,
      }
    default:
      return state
  }
}