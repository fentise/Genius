import React,{ Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import './style/login.css';
import Header from '../header/Header';
import LoginDetail from './LoginDetail';
import {cookie} from '../cookie/cookie';
import { loginRequest, restoreRegisterState } from '../../actions/actions';

class Login extends Component {

  componentWillUpdate( props ){
    if( props.loginState.message == "success" ) {
       this.props.history.replace('/');
    }
  }

  login = ( tips ) =>
  {
    const LoginRequestURL = "http://127.0.0.1:8080/login";
    this.props.loginRequest( LoginRequestURL, tips );
  }

  render(){
    return(
      <div>
        <Header  menu={ ['4']}/>
        <LoginDetail
            history={ this.props.history }
            registerState={ this.props.registerState }
            login={ this.login }
        />
      </div>
    );
  }
}

function mapStateToProps( state) {
   return {
     loginState: state.login,
     registerState: state.registerState
   }
}

export default connect(mapStateToProps , {loginRequest, restoreRegisterState})(Login);
