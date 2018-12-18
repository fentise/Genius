import React, { Component } from 'react';
import './style/register.css';
import RegisterDetail from './RegisterDetail';

class Register extends Component{
  constructor(props){
    super(props);
  }

  render(){
    return(
       <div className="register">
          <RegisterDetail history={ this.props.history }/>
       </div>
    );
  }
}

export default Register;
