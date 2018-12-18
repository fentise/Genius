import React,{ Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';
import './style/login.css';
import LoginDetail from './LoginDetail';
import { loginRequest } from '../../actions/actions';

class Login extends Component {
  // this.props.loginRequest()
  render(){

    return(
      <div>
        <LoginDetail
            submitLogin={ this.props.loginRequest }
            history={ this.props.history }
        />
      </div>
    );
  }
}

function mapStateToProps( state ){
  return {
    submitLogin: state.login
  }
}

export default connect(mapStateToProps , { loginRequest })(Login);
