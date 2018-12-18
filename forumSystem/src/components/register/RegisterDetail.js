import {
  Form, Input, Tooltip, Icon, Cascader, Select, Row, Col, Checkbox, Button, AutoComplete,
} from 'antd';
import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import 'antd/dist/antd.css';

import Header from '../header/Header';
import Footer from '../footer/Footer';
import './style/registerDetail.css';
import { registerRequest } from  '../../actions/actions';

const FormItem = Form.Item;
const Option = Select.Option;
const AutoCompleteOption = AutoComplete.Option;

// const residences = [
//   {
//   value: 'zhejiang',
//   label: 'Zhejiang',
//   children: [{
//     value: 'hangzhou',
//     label: 'Hangzhou',
//     children: [{
//       value: 'xihu',
//       label: 'West Lake',
//     }],
//   }],
// },
//   {
//   value: 'jiangsu',
//   label: 'Jiangsu',
//   children: [{
//     value: 'nanjing',
//     label: 'Nanjing',
//     children: [{
//       value: 'zhonghuamen',
//       label: 'Zhong Hua Men',
//     }],
//   }],
// },
// ];

class RegisterForm extends React.Component {
  state = {
    confirmDirty: false,
    autoCompleteResult: [],
    email: "",
    password: "",
    userName: "",
  };

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        // console.log('Received values of form: ', values);
        this.setState({
          email: values.emali,
          password: values.password,
          userName: values.userName,
        });
        this.props.registerRequest( this.state, this.props.history );
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
    // console.log("form.getFieldValue('agreement')", form.getFieldValue('agreement'));;
    if ( !form.getFieldValue('agreement')) {
      callback('注册须同意《用户协议》');
    } else {
      callback();
    }
  }

  // handleWebsiteChange = (value) => {
  //   let autoCompleteResult;
  //   if (!value) {
  //     autoCompleteResult = [];
  //   } else {
  //     autoCompleteResult = ['.com', '.org', '.net'].map(domain => `${value}${domain}`);
  //   }
  //   this.setState({ autoCompleteResult });
  // }

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
    // const prefixSelector = getFieldDecorator('prefix', {
    //   initialValue: '86',
    // })(
    //   <Select style={{ width: 70 }}>
    //     <Option value="86">+86</Option>
    //     <Option value="87">+87</Option>
    //   </Select>
    // );

    const websiteOptions = autoCompleteResult.map(website => (
      <AutoCompleteOption key={website}>{website}</AutoCompleteOption>
    ));

    return (
      <div>
        <Header />
        <div className="register-content">
           <div className="register-title">
             用户注册
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
        <Footer />
      </div>
    );
  }
}

// <FormItem
//   {...formItemLayout}
//   label="Captcha"
//   extra="We must make sure that your are a human."
// >
//   <Row gutter={8}>
//     <Col span={12}>
//       {getFieldDecorator('captcha', {
//         rules: [{ required: true, message: 'Please input the captcha you got!' }],
//       })(
//         <Input />
//       )}
//     </Col>
//     <Col span={12}>
//       <Button>Get captcha</Button>
//     </Col>
//   </Row>
// </FormItem>
// <FormItem
//   {...formItemLayout}
//   label="所在地区"
// >
//   {getFieldDecorator('residence', {
//     initialValue: ['广东', '广州', '天河区'],
//     rules: [{ type: 'array', required: true, message: '请选择您所在的地区' }],
//   })(
//     <Cascader options={residences} />
//   )}
// </FormItem>
// <FormItem
//   {...formItemLayout}
//   label="手机"
// >
//   {getFieldDecorator('phone', {
//     rules: [{ required: false, message: '请输入正确的手机号码' }],
//   })(
//     <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
//   )}
// </FormItem>

const RegisterDetail = Form.create()(RegisterForm);

export default connect( null, {registerRequest})(RegisterDetail);
