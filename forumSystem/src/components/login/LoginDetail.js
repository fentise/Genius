import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import 'antd/dist/antd.css';

import './style/loginDetail.css';
import Header from '../header/Header';
import Footer from '../footer/Footer';
import { loginRequest } from '../../actions/actions';

const FormItem = Form.Item;
class LoginDetailAntd extends Component {
  constructor(props){
    super(props);
    this.state = {
      userName: "",
      password: ""
    }
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
        this.setState({
          userName: values.userName,
          password:values.password
         });
        this.props.loginRequest( this.state, this.props.history);
      }
    });
  }

  render(){
    const { getFieldDecorator } = this.props.form;

    return(
      <div className="loginDetail">
        <div className="loginArea-header">
            <Header />
        </div>
        <div className="login-content">
          <Form onSubmit={this.handleSubmit} className="login-form" >
            <div className="login-title">
              CS论坛
            </div>
            <FormItem>
              {getFieldDecorator('userName', {
                rules: [{ required: true, message: '请输入用户名' }],
              })(
                <Input prefix={<Icon type="user" style={{ fontSize: 13 }} />} placeholder="用户名" />
              )}
            </FormItem>
            <FormItem>
              {getFieldDecorator('password', {
                rules: [{ required: true, message: '请输入密码' }],
              })(
                <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} type="password" placeholder="密码" />
              )}
            </FormItem>
            <FormItem>
              {getFieldDecorator('remember', {
                valuePropName: 'checked',
                initialValue: true,
              })(
                <Checkbox>记住我</Checkbox>
              )}
              <a className="login-form-forgot">忘记密码？</a>
              <Button type="primary" htmlType="submit" className="login-form-button">
                登录
              </Button>
               <Link to="/register"><a>新用户，去注册</a></Link>
            </FormItem>
          </Form>
        </div>

        <div className="loginDetail-footer">
           <Footer />
        </div>

      </div>
    );
  }
}

const LoginDetail = Form.create()(LoginDetailAntd);

export default connect( null, {loginRequest})(LoginDetail);
