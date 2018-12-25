import React,{ Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Breadcrumb } from 'antd';
import 'antd/dist/antd.css';
import { cookie }  from '../cookie/cookie';
import { logOut } from '../../actions/actions';
import './style/header.css';
const { Header, Content, Footer } = Layout;
class HeaderForLogin extends Component {
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
              <Menu.Item key="3"><Link to={`/writeArticle/${ this.props.loginState.userId }`}>发帖</Link></Menu.Item>

              <Menu.Item key="4" className="header-logOut" onClick={ this.props.logOut.bind(this) }><Link to="/">退出</Link></Menu.Item>
              <Menu.Item key="5" className="header-logOut"><Link to={`/userInfo/${ this.props.loginState.userId }`}>{ cookie.get('userName') }</Link></Menu.Item>
              </Menu>
            </Header>

        </Layout>
    );
  }
}

function mapStateToProps( state ){
   return { loginState: state.login };
}
export default connect( mapStateToProps, {logOut})(HeaderForLogin);
