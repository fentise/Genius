import {
  Form, Input, Tooltip, Icon, Cascader, Select, Row, Col, Checkbox, Button, AutoComplete,
} from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import 'antd/dist/antd.css';


import Footer from '../footer/Footer';
import './style/registerDetail.css';
import { registerRequest } from  '../../actions/actions';

const FormItem = Form.Item;
const Option = Select.Option;
const AutoCompleteOption = AutoComplete.Option;

class RegisterForm extends React.Component {
  constructor(props){
    super(props);
  }

  state = {
    confirmDirty: false,
    autoCompleteResult: [],
   };

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        var tips = {
          email: values.email,
          password: values.password,
          userName: values.nickname,
        };
        this.props.register( tips );
      }
    });
  }

  handleConfirmBlur = (e) => {
    const value = e.target.value;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  }

  compareToFirstPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次密码输入不一致，请重新输入');
    } else {
      callback();
    }
  }

  validateToNextPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && this.state.confirmDirty) {
      form.validateFields(['confirm'], { force: true });
    }
    callback();
  }

  confirmAgreement = (rule, value, callback) => {
    const form = this.props.form;
    if ( !form.getFieldValue('agreement')) {
      callback('注册须同意《用户协议》');
    } else {
      callback();
    }
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    const { autoCompleteResult } = this.state;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 24,
          offset: 0,
        },
        sm: {
          span: 16,
          offset: 8,
        },
      },
    };

    const websiteOptions = autoCompleteResult.map(website => (
      <AutoCompleteOption key={website}>{website}</AutoCompleteOption>
    ));

    return (
        <div className="register-content">
           <div className="register-title">
             ANT论坛用户注册
           </div>
           <Form onSubmit={this.handleSubmit} className="register-form">
            <FormItem
              {...formItemLayout}
              label="邮箱"
            >
              {getFieldDecorator('email', {
                rules: [{
                  type: 'email', message: '请输入正确的邮箱地址',
                }, {
                  required: true, message: '请输入邮箱',
                }],
              })(
                <Input />
              )}
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="密码"
            >
              {getFieldDecorator('password', {
                rules: [{
                  required: true, message: '请输入密码',
                }, {
                  validator: this.validateToNextPassword,
                }],
              })(
                <Input type="password" />
              )}
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="确认密码"
            >
              {getFieldDecorator('confirm', {
                rules: [{
                  required: true, message: '请再次输入密码！',
                }, {
                  validator: this.compareToFirstPassword,
                }],
              })(
                <Input type="password" onBlur={this.handleConfirmBlur} />
              )}
            </FormItem>
            <FormItem
              {...formItemLayout}
              label={(
                <span>
                  用户名&nbsp;
                  <Tooltip title="登录后将显示该名字">
                    <Icon type="question-circle-o" />
                  </Tooltip>
                </span>
              )}
            >
              {getFieldDecorator('nickname', {
                rules: [ { required: true, message: '请输入用户名', whitespace: true }],
              })(
                <Input />
              )}
            </FormItem>


            <FormItem {...tailFormItemLayout}>
              {getFieldDecorator('agreement', {
                  rules: [ { validator: this.confirmAgreement }]
              })(
                  <Checkbox>我已阅读并同意<Link to="/userAgreement" target="_blank">《用户协议》</Link></Checkbox>
              )}
            </FormItem>
            <FormItem {...tailFormItemLayout}>
              <Button type="primary" htmlType="submit" className="register-button">注册</Button>
            </FormItem>

           </Form>
        </div>

    );
  }
}

const RegisterDetail = Form.create()(RegisterForm);

export default RegisterDetail;
