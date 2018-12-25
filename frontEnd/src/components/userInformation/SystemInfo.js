import React,{ Component } from 'react';
import {  List  } from 'antd';
import 'antd/dist/antd.css';
import axios from 'axios';
import { cookie }  from '../cookie/cookie';

class SystemInfo extends Component {
  constructor(props){
    super(props);
  }

  state = {
    systemInfo: [],
  };

  componentWillMount(){
        this.getSystemInfo( this.GetSystemInfoURL );
   }

  GetSystemInfoURL = `http://127.0.0.1:8080/userNotify/${ cookie.get('userId') }`;
  getSystemInfo( url ){
     return axios.get( url )
            .then( res => {
              if ( res.status === 200 ){
                this.setState({ systemInfo: res.data.reminders}) ;
              }
            })
            .catch( err => {
              console.log("getSystemInfoError:", err);
              alert("获取系统通知失败，请检查网络！");
            })
  }

render(){
    return(
      <div className="systemInfo">
        <List
          size="large"
          bordered
          dataSource={ this.state.systemInfo }
          renderItem={item => (<List.Item>{item}</List.Item>)}
        />
      </div>
    );
  }
}

export default SystemInfo;
