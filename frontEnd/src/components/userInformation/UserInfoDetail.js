import React,{ Component } from 'react';
import { Route, Switch, Link } from 'react-router-dom';
import './style/userInfoDetail.css';
import { Layout, Menu, Icon, List  } from 'antd';
import 'antd/dist/antd.css';
import SystemInfo from './SystemInfo';
import Subscription from './Subscription';
import { cookie }  from '../cookie/cookie';

const { Header, Sider, Content } = Layout;
class UserInfoDetail extends Component{
  constructor(props){
    super(props);
  }
  state = {
      collapsed: false,
    };

    toggle = () => {
      this.setState({
        collapsed: !this.state.collapsed,
      });
    }
    
  render(){

    return(
      <div className="userInfoDetail">
        <Layout>
          <Sider
            trigger={ null }
            collapsible
            collapsed={ this.state.collapsed }
          >

              <Menu theme="dark" mode="inline" defaultSelectedKeys={['0']}>
                <Menu.Item key="1">
                  <Link to={`/userInfo/${cookie.get('userId')}/systemInfo`}><Icon type="notification" />
                  <span>系统通知</span></Link>
                </Menu.Item>
                <Menu.Item key="2">
                  <Link to={`/userInfo/${cookie.get('userId')}/mySubscription`}><Icon type="check" />
                  <span>订阅规则</span></Link>
                </Menu.Item>
              </Menu>
          </Sider>
          <Layout>
              <Header style={{ background: '#fff', padding: 0, height: '0px' }}>
                <Icon
                  className="trigger"
                  type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                  onClick={this.toggle}
                />
              </Header>
              <Content style={{ margin: '24px 16px', padding: 24, background: '#fff', minHeight: 280,}}
              >
                <Switch>
                  <Route path="/userInfo/:userId/systemInfo" component={ SystemInfo } />
                  <Route path="/userInfo/:userId/mySubscription" component={ Subscription }/>
                </Switch>
              </Content>
          </Layout>
        </Layout>
      </div>
    );
  }
}

export default UserInfoDetail;
