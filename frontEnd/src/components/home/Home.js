import React,{ Component } from 'react';
import axios from 'axios';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Comment, Icon, Tooltip, Avatar, List, Tag } from 'antd';

import moment from 'moment';
import 'antd/dist/antd.css';

import { cookie }  from '../cookie/cookie';
import Header from '../header/Header';
import HeaderForLogin from '../header/HeaderForLogin';
import HomeDetail from './HomeDetail';
import './style/home.css';

class Home extends Component {
  constructor(props){
    super(props);
  }

  whichHeader(){
    if(cookie.get('loginState') == "1" )
      return ( <HeaderForLogin menu={["1"]} /> );
    else return ( <Header menu={["1"]}/> )
  }

render(){
  console.log("HomeCookie:", document.cookie);
  // console.log("cookie.get('userId')" );
    return(
      <div className="home">
        { this.whichHeader() }
        <div className="home-content">
          <HomeDetail
              history={ this.props.history }
           />
        </div>
        <div className="home-footer">
        </div>
      </div>
      );
  }
}

function mapStateToProps( state ){
   return { loginState: state.login };
}

export default connect( mapStateToProps, null)(Home);
