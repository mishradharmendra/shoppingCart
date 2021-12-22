import axios from 'axios'
import { CART_CLEAR_ITEMS } from '../constants/cartConstants'
import {
  WALLET_CREATE_REQUEST,
  WALLET_CREATE_SUCCESS,
  WALLET_CREATE_FAIL,
  WALLET_DETAILS_FAIL,
  WALLET_DETAILS_SUCCESS,
  WALLET_DETAILS_REQUEST,
  WALLET_PAY_FAIL,
  WALLET_PAY_SUCCESS,
  WALLET_PAY_REQUEST,
  WALLET_LIST_MY_REQUEST,
  WALLET_LIST_MY_SUCCESS,
  WALLET_LIST_MY_FAIL,
  WALLET_LIST_FAIL,
  WALLET_LIST_SUCCESS,
  WALLET_LIST_REQUEST,
  WALLET_DELIVER_FAIL,
  WALLET_DELIVER_SUCCESS,
  WALLET_DELIVER_REQUEST,
} from '../constants/walletConstants'
import { logout } from './userActions'

export const createWallet = (wallet) => async (dispatch, getState) => {
  try {
    dispatch({
      type: WALLET_CREATE_REQUEST,
    })

    const {
      userLogin: { userInfo },
    } = getState()

    const config = {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${userInfo.token}`,
      },
    }

    var customerId = "customerId";
    var newVal = userInfo._id;


    wallet[customerId] = newVal;

    const { data } = await axios.post(`/api/wallet/createWallet`, wallet, config)

    dispatch({
      type: WALLET_CREATE_SUCCESS,
      payload: data,
    })
  } catch (error) {
    const message =
      error.response && error.response.data.message
        ? error.response.data.message
        : error.message
    if (message === 'Not authorized, token failed') {
      dispatch(logout())
    }
    dispatch({
      type: WALLET_CREATE_FAIL,
      payload: message,
    })
  }
}

export const getWalletDetails = (id) => async (dispatch, getState) => {
  try {
    dispatch({
      type: WALLET_DETAILS_REQUEST,
    })

    const {
      userLogin: { userInfo },
    } = getState()

    const config = {
      headers: {
        Authorization: `Bearer ${userInfo.token}`,
      },
    }

    const { data } = await axios.get(`/api/wallet/${id}`, config)

    dispatch({
      type: WALLET_DETAILS_SUCCESS,
      payload: data,
    })
  } catch (error) {
    const message =
      error.response && error.response.data.message
        ? error.response.data.message
        : error.message
    if (message === 'Not authorized, token failed') {
      dispatch(logout())
    }
    dispatch({
      type: WALLET_DETAILS_FAIL,
      payload: message,
    })
  }
}

export const getCustomerWallet = (id) => async (dispatch, getState) => {
  try {
    dispatch({
      type: WALLET_DETAILS_REQUEST,
    })

    const {
      userLogin: { userInfo },
    } = getState()

    const config = {
      headers: {
        Authorization: `Bearer ${userInfo.token}`,
      },
    }

    const { data } = await axios.get(`/api/wallet/customer/${userInfo._id}`, config)
    console.log("data from action " + JSON.stringify(data));

    dispatch({
      type: WALLET_DETAILS_SUCCESS,
      payload: data,
    })
  } catch (error) {
    const message =
      error.response && error.response.data.message
        ? error.response.data.message
        : error.message
    if (message === 'Not authorized, token failed') {
      dispatch(logout())
    }
    dispatch({
      type: WALLET_DETAILS_FAIL,
      payload: message,
    })
  }
}
