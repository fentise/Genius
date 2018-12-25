import React,{ Component } from 'react';
import { Link } from 'react-router-dom';
import { Layout, Menu, Breadcrumb } from 'antd';
import 'antd/dist/antd.css';
import './style/header.css';
const { Header, Content, Footer } = Layout;
class Head extends Component {
  constructor(props){
    super(props);
  }

  render(){
    return(
         <Layout className="layout">
            <Header className="header">
              <div className="logo" />
              <Menu
                theme="dark"
                mode="horizontal"
                defaultSelectedKeys={ this.props.menu }
                style={{ lineHeight: '64px' }}
              >
                <Menu.Item key="1"><Link to="/">首页</Link></Menu.Item>
                <Menu.Item key="5" className="header-logOut"><Link to="/register">注册</Link></Menu.Item>
                <Menu.Item key="4" className="header-logOut"><Link to="/login">登录</Link></Menu.Item>
              </Menu>

            </Header>

        </Layout>
    );
  }
}
// <Footer style={{ textAlign: 'center' }}>
//   Ant Design ©2018 Created by Ant UED
// </Footer>
// <div className="header-main">
//      <Link to="/"> <img className="header-main-pic" src="./logo.jpg" alt="logo"/></Link>
// </div>
// <ul className="header-navbar">
//   <li><Link to="/">首页</Link></li>
//   <li><Link to="/discuss">讨论</Link></li>
// </ul>
// <div className="header-loginArea">
//   <a href="/login" className="nav-account-login" >登录</a>/
//   <a href="/register" className="nav-account-reg" data-permalink="">注册</a>
// </div>
export default Head;
