import React,{ Component } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';

import HeaderForLogin from '../header/HeaderForLogin';
import UserInfoDetail from './UserInfoDetail';
import { cookie }  from '../cookie/cookie';
import { getSystemInfo, getUserSubscription } from '../../actions/actions';
import './style/userInfo.css';


class UserInfo extends Component {
  constructor(props){
    super(props);
  }

   state = {
     systemInfo: [ "默认通知" ],
     subscription: [ {subscriptionStatus:1, id:0, content:"这是默认订阅规则" } ],
   }


  render(){
    return(
      <div className="userInfo">
          <HeaderForLogin menu={['5']}/>
          <div>
            <UserInfoDetail
                systemInfo={ this.state.systemInfo }
                subscription={ this.state.subscription }
            />
          </div>
      </div>
    );
  }
}

function mapStateToProps( state ) {
   return {
     loginState: state.login,
   }
}
export default connect( mapStateToProps, null )(UserInfo);
