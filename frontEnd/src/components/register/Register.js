import React, { Component } from 'react';
import { connect } from 'react-redux';
import { registerRequest } from  '../../actions/actions';

import './style/register.css';
import Header from '../header/Header';
import RegisterDetail from './RegisterDetail';

class Register extends Component{
  constructor(props){
    super(props);
  }

 state = {
   email: "",
   password: "",
   userName: "",
 }

 componentWillUpdate( props ){
   if( props.registerState.registerState ){
     this.props.history.replace('/login');
   }
 }
 register = ( tips ) =>
 {
    const RegisterRequestURL = "http://127.0.0.1:8080/register";
    this.props.registerRequest( RegisterRequestURL, tips );
    // console.log("registerState:", this.props.registerState.registerState );
 }

  render(){
    return(
       <div className="register">
         <Header  menu={ ['5']}/>
          <RegisterDetail
             history={ this.props.history }
             registerState = { this.props.registerState }
             register= { this.register }
          />
       </div>
    );
  }
}

function mapStateToProps(state){
  return{
    registerState: state.registerState,
  }
}
export default connect( mapStateToProps, {registerRequest} )(Register);
