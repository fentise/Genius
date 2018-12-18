import React,{ Component } from 'react';
import { Link } from 'react-router-dom';

import './style/header.css';

class Header extends Component {
  constructor(props){
    super(props);
  }

  render(){
    return(
      <div className="header">
         <div className="header-main">
              <Link to="/"> <img className="header-main-pic" src="./logo.jpg" alt="logo"/></Link>
         </div>
      </div>
    );
  }
}
// <ul className="header-navbar">
//   <li><Link to="/">首页</Link></li>
//   <li><Link to="/discuss">讨论</Link></li>
// </ul>
// <div className="header-loginArea">
//   <a href="/login" className="nav-account-login" >登录</a>/
//   <a href="/register" className="nav-account-reg" data-permalink="">注册</a>
// </div>
export default Header;
