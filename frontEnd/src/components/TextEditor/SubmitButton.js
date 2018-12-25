import React, { Component } from 'react';
import { Button } from 'antd';
import './style/SubmitButton.css';


class SubmitButton extends Component {
   constructor(props){
    super(props);
   }
  //
   render(){
     return(
       <Button type="primary" className="SubmitButton" onClick={ this.props.submitContent }>发表</Button>
     );
   }
}

export default SubmitButton;
