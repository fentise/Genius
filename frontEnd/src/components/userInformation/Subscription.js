import React,{ Component } from 'react';
import {  List, Checkbox, Row, Col, Button  } from 'antd';
import 'antd/dist/antd.css';
import axios from 'axios';
import { cookie }  from '../cookie/cookie';
import './style/subscription.css';

const CheckboxGroup = Checkbox.Group;
const plainOptions = ["我发布的帖子被评论", "我发布的帖子被点赞", "我发布的评论被回复",
                      "我发布的评论被点赞", "我发布的回复被点赞", "我被关注", "我关注的帖子有新评论"];

class Subscription extends Component {
  constructor(props){
    super(props);
  }

  state={
    zero: 0, one: 0, two: 0, three: 0, four: 0, five: 0, six: 0,
    notice: [],
    checkedList: [],
    indeterminate: true,
    checkAll: false,
  }

  // componentDidUpdate( state ){
  //    this.updateSubscription( this.updateChekckGroupURL );
  // }

  onChange = (checkedList) => {
     this.setState({
       checkedList,
       indeterminate: !!checkedList.length && (checkedList.length < plainOptions.length),
       checkAll: checkedList.length === plainOptions.length,
     });

     // this.state.checkedList.map( item =>{
     //    switch( item ){
     //     case "我发布的帖子被评论":
     //       this.setState({ zero : 1});
     //       break;
     //     case "我发布的帖子被点赞":
     //       this.setState({ one : 1});
     //       break;
     //     case "我发布的评论被回复":
     //       this.setState({ two : 1});
     //       break;
     //     case "我发布的评论被点赞":
     //       this.setState({ three : 1});
     //       break;
     //     case "我发布的回复被点赞":
     //       this.setState({ four : 1});
     //       break;
     //     case "我被关注":
     //       this.setState({ five : 1});
     //       break;
     //     case "我关注的帖子有新评论":
     //       this.setState({ six : 1});
     //       break;
     //   }  })
     // this.updateSubscription( this.updateChekckGroupURL ,state );
   }

   onCheckAllChange = (e) => {
     this.setState({
       checkedList: e.target.checked ? plainOptions : [],
       indeterminate: false,
       checkAll: e.target.checked,
     });
   }

  componentWillMount(){
      this.getUserSubscription( this.GetUserSubscriptionURL );
   }

  GetUserSubscriptionURL = `http://127.0.0.1:8080/userNotify/userSubscription/${ cookie.get('userId') }`;
  getUserSubscription( url ){
     return axios.get( url )
            .then( res => {
              if ( res.status === 200 ){
                this.updateChekckGroup( res.data.notice );
              }
            })
            .catch( err => {
              console.log("getUserSubscriptionError:", err);
              alert("获取用户订阅规则失败，请检查网络！");
            })
  }

  updateChekckGroupURL = `http://127.0.0.1:8080/userNotify/userSubscription/update/${ cookie.get('userId') }`;
  updateChekckGroup( notice ){
    var tips = [];
    if( notice.length > 0 )
     notice.map( item => {
        if( item.subscriptionStatus == 1 ){
          tips.push( item.content);
        }
     });
     this.setState({ checkedList: tips });
  }

  updateSubscription( url ) {
    var actions = {  n0: 0, n1: 0, n2: 0, n3: 0, n4: 0, n5: 0, n6: 0 };
    if( this.state.checkedList.length > 0 ){
        this.state.checkedList.map( item =>{
           switch( item ){
            case "我发布的帖子被评论":
              actions.n0 = 1;
              break;
            case "我发布的帖子被点赞":
              actions.n1 = 1;
              break;
            case "我发布的评论被回复":
              actions.n2 = 1;
              break;
            case "我发布的评论被点赞":
              actions.n3 = 1;
              break;
            case "我发布的回复被点赞":
              actions.n4 = 1;
              break;
            case "我被关注":
              actions.n5 = 1;
              break;
            case "我关注的帖子有新评论":
              actions.n6 = 1;
              break;
          }  })
        console.log("acitons", actions );
      }
      return axios.post( url, { actions: actions} )
            .then( res => {
              if( res.status === 200 ){
                  if( res.data.result ){
                     this.getUserSubscription( this.GetUserSubscriptionURL );
                     alert("更新成功");
                }
              }
            })
            .catch( err => {
              console.log("changeMySubscriptionError:", err);
              alert("更新用户订阅规则失败，请检查网络！");
            })
  }
render(){
    return(
      <div className="subscription">
         <div style={{ borderBottom: '1px solid #E9E9E9' }}>
           <Checkbox
             className="checkAll"
             indeterminate={this.state.indeterminate}
             onChange={this.onCheckAllChange}
             checked={this.state.checkAll}
           >
             全选
           </Checkbox>
         </div>
         <br />
           <Col span={5}>
             <CheckboxGroup className="Checkbox" options={plainOptions} value={ this.state.checkedList }onChange={ this.onChange } >
             </CheckboxGroup>
            <Button className="button" type="primary" onClick={ this.updateSubscription.bind(this, this.updateChekckGroupURL ) }>确定</Button>
         </Col>
      </div>

    );
  }
}

export default Subscription;
