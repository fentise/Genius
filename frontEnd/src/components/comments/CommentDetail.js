import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import axios from 'axios';
import { Comment, Tooltip, List, Icon, Pagination } from 'antd';
import moment from 'moment';

import { cookie }  from '../cookie/cookie';
import Header from '../header/Header';
import TextEditor from '../TextEditor/Draft';
import './style/commentDetail.css';

class CommentDetail extends Component {
  constructor(props) {
    super(props);
  }
  state = {
      comment: [],
  }

  componentWillMount(){
    const GetArticleURL = `http://127.0.0.1:8080/articleDetail/${this.props.articleId}`;
    this.getArticle( GetArticleURL, this.props.articleId, cookie.get('userId') );
  }

  getArticle( url, articleId, userId ){
     return axios.post( url, {
               articleId: parseInt(articleId) ,
               userId: userId,
            })
            .then( res => {
             if(res.status == 200 )
               this.setState( {
                 comment: res.data.comment,
                } );
             } )
            .catch( function(error){
              alert('获取详情数据失败');
              console.log(error);
           });
  }
  HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
  }

  renderData( commentData, listData, loginState ){
      commentData.map( comment => listData.push({
        Id: comment.commentId,
        author: comment.commentUserName,
        avatar: comment.commentUserProfilePhoto,
        content: comment.content,
        datetime: comment.createTime,

        likeNum: comment.likeCount,
        replyNum: comment.replyCount,

        likeThis: loginState?comment.liked:0,
        repliedThis: loginState?comment.repliedThis:0,
      }) );
     return listData;
  }

  changeActionsRequest( url, commentId ){
     if ( cookie.get('loginState')  == "1" ){
        axios.post( url,
        {
       		commentId: parseInt(commentId),
       		userId: cookie.get('userId'),
   	    })
         .then( res => {
            if( res.status === 200){
              console.log( " likeComment:", res.data);
                if( res.data.code == 0){
                  // console.log("changeActionsRequestRes", res.data);
                  const GetArticleURL = `http://127.0.0.1:8080/articleDetail/${this.props.articleId}`;
                  this.getArticle( GetArticleURL, this.props.articleId, cookie.get('userId') );
                }
                else{
                  alert("操作失败，请重试");
                }
            }
         })
         .catch( err =>{
             alert("操作失败，请检查网络，稍后再试");
             console.log("changeActionsErr:", err);
         });
       }
    else alert("您尚未登录，请登录后再操作！");
  }

  onClickAction( id, type ){
     const LikeURL = "http://127.0.0.1:8080/likeComment";
     const UnLikeURL = "http://127.0.0.1:8080/dislikeCommment";
     var finishedData  = [];
     finishedData = this.renderData( this.state.comment, [], cookie.get('loginState'));
     finishedData.map( item =>{
         if( item.Id == id ){
             if(type == "heart") {
                if( item.likeThis == 0 ){
                  this.changeActionsRequest(LikeURL,id); }
                else{
                  this.changeActionsRequest(UnLikeURL,id); }
             }
           }
         })
  }
  
  itemRender( current, type, originalElement ){
      if (type === 'prev') {
        return <a>Previous</a>;
      } if (type === 'next') {
        return <a>Next</a>;
      }
      return originalElement;
  }

  render(){
    var hope = [];

    hope = this.renderData( this.state.comment, [], cookie.get('loginState') );

    const IconText = ({ id, type, text, theme="outlined" }) =>(
        <span>
          <Icon type={type} style={{ marginRight: 8 }}  theme={ theme }
                onClick={ this.onClickAction.bind(this, id, type) } />
          {text}
        </span>
        )

    return(
      <div className="commentDetail">
          <List
            className="comment-list"
            header={`${hope.length} 评论`}
            itemLayout="horizontal"
            pagination={{
              pageSize: 10,
              showQuickJumper: true,
              showSizeChanger: false,
              defaultCurrent: 6,
              itemRender: this.itemRender(),
            }}
            dataSource={hope}
            renderItem={item => (
              <Comment
                className="comment-actions"
                actions={[
                          <IconText id={ item.Id } type="heart" text={ "我喜欢" }
                             theme={ item.likeThis? 'filled' : 'outlined'} />,
                          // <IconText id={ item.Id } type="message" text={ "我的评论"}
                          //    theme={ item.repliedThis? 'filled' : 'outlined'} />,
                          <IconText id={ item.Id } type="like" text={ item.likeNum }
                             theme={ 'filled'} />,
                          <IconText id={ item.Id } type="user" text={ item.replyNum }
                             theme={ 'outlined'} />,
                        ]}
                author={<h3 dangerouslySetInnerHTML={  {__html: item.author} }></h3>}
                avatar={item.avatar}
                content={<div  dangerouslySetInnerHTML={  {__html: this.HTMLDecode(item.content)} }></div>}
                datetime={<p dangerouslySetInnerHTML={  {__html: item.datetime} }></p>}
              />
            )}
          />
          <TextEditor
              className="comment-editor"
              articleId={ this.props.articleId }
              getNewReply={ this.getArticle.bind(this, `http://127.0.0.1:8080/articleDetail/${this.props.articleId}`, this.props.articleId,  cookie.get('userId') )}
          />
      </div>

    );
  }
}
function mapStateToProps( state ){
   return {
     loginState: state.login,
    };
}
export default connect( mapStateToProps, null)(CommentDetail);
