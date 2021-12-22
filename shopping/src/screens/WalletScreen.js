import React, { useState, useEffect } from 'react'
import { Table, Form, Button, Row, Col } from 'react-bootstrap'
import { LinkContainer } from 'react-router-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import Message from '../components/Message'
import Loader from '../components/Loader'
import { getUserDetails, updateUserProfile } from '../actions/userActions'
import { getCustomerWallet, createWallet } from '../actions/walletActions'
import { USER_UPDATE_PROFILE_RESET } from '../constants/userConstants'

const WalletScreen = ({ location, history }) => {
  
  const dispatch = useDispatch()
  const [sdkReady, setSdkReady] = useState(true)

   const customerWallet = useSelector((state) => state.customerWallet)
  const { wallet, loading, error } = customerWallet


  const userLogin = useSelector((state) => state.userLogin)
  const { userInfo } = userLogin

  const userUpdateProfile = useSelector((state) => state.userUpdateProfile)
  const { success } = userUpdateProfile

  let walletId = '';
  let currentBalance = 0;
  if (!loading) {
    //   Calculate prices
   console.log(wallet.id + "****************")
   walletId = wallet.id;
   currentBalance = wallet.currentBalance;
  }

  useEffect(() => {
    console.log("into wallet")
    if (!userInfo) {
      history.push('/login')
    } else if (!wallet || wallet.id === '') {
        dispatch({ type: USER_UPDATE_PROFILE_RESET })
        //dispatch(getUserDetails('profile'))
        dispatch(getCustomerWallet())
        console.log(JSON.stringify(wallet) + "*********")
    }
  }, [dispatch, success, wallet])

  const createWalletHandler = () => {
    dispatch(
      createWallet({
        currentBalance: 1000,
      })
    )
  }

  return (
    <Row>
      
      <Col md={9}>
        <h2>My Wallet {walletId} </h2>

        {/* {loading ? (
          <Loader />
        ) : error ? (
          <Message variant='danger'>{error}</Message>
        ) : (
          <Table striped bordered hover responsive className='table-sm'>
            <thead>
              <tr>
                <th>ID</th>
                <th>CURRENT BALANCE</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {wallets.map((wallet) => (
                <tr key={wallet._id}>
                  <td>{wallet._id}</td>
                  <td>{wallet.currentBalance}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        )} */}

          {walletId && walletId.length ? 
            <Table striped bordered hover responsive className='table-sm'>
            <thead>
              <tr>
                <th>ID</th>
                <th>CURRENT BALANCE</th>
              </tr>
            </thead>
            <tbody>
                <tr key={walletId}>
                  <td>{walletId}</td>
                  <td>{currentBalance}</td>
                </tr>
            </tbody>
          </Table>
        : 
          <div>
            No Wallet Found. Click Below to create Wallet with initial benefit of 1000<br/><br/>
            <button onClick={createWalletHandler}>Create Wallet</button>
          </div>
          
          }
          
      </Col>
    </Row>
  )
}

export default WalletScreen
