import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import 'antd/dist/antd.css';

import './style/loginDetail.css';
import Footer from '../footer/Footer';
import { cookie }  from '../cookie/cookie';
import { loginRequest, restoreRegisterState } from '../../actions/actions';

const FormItem = Form.Item;
class LoginDetailAntd extends Component {
  constructor(props){
    super(props);
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
          cookie.set('userMail', values.userName, 7);
          cookie.set('password', values.password, 7);
          cookie.set('remember', values.remember, 7);

        var tips = {
          userName: values.userName,
          password: values.password,
          rememberMe: values.remember,
        }
        this.props.login(tips);
       }
     });
   }

   judgeNewUser( registerState ){
      if( registerState.registerState == 0 )
      return {
         email: cookie.get('userMail'),
         password: cookie.get('password'),
         remember: cookie.get('remember'),
      }
      else{
        var tips = {
          email: registerState.newEmail,
          password: "",
          remember: false,
        }
        this.props.restoreRegisterState();
        return  tips;
      }
   }

  render(){
    const { getFieldDecorator } = this.props.form;
    return(
      <div className="loginDetail">
        <div className="login-content">
          <Form onSubmit={this.handleSubmit} className="login-form" >
              <div className="login-title">
                ANT论坛
              </div>
              <FormItem>
                {getFieldDecorator('userName', {
                  rules: [{ required: true, message: '请输入注册邮箱' }],
                  initialValue: this.judgeNewUser( this.props.registerState ).email,
                })(
                  <Input prefix={<Icon type="mail" style={{ fontSize: 13 }} />} placeholder="注册邮箱" />
                )}
              </FormItem>
              <FormItem>
                {getFieldDecorator('password', {
                  rules: [{ required: true, message: '请输入密码' }],
                  initialValue: this.judgeNewUser( this.props.registerState ).password,
                })(
                  <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} type="password" placeholder="密码" />
                )}
              </FormItem>
              <FormItem>
                {getFieldDecorator('remember', {
                  valuePropName: 'checked',
                  initialValue: this.judgeNewUser( this.props.registerState ).remember,
                })(
                  <Checkbox>记住我(7天内免登录)</Checkbox>
                )}
                <a className="login-form-forgot">忘记密码？</a>
                <Button type="primary" htmlType="submit" className="login-form-button">
                  登录
                </Button>
                 <Link to="/register"><a>新用户，去注册</a></Link>
              </FormItem>
          </Form>
        </div>

      </div>
    );
  }
}

const LoginDetail = Form.create()(LoginDetailAntd);

export default connect(null, {restoreRegisterState})(LoginDetail);
