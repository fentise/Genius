import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import axios from 'axios';

import { cookie }  from '../cookie/cookie';
import Header from '../header/Header';
import HeaderForLogin from '../header/HeaderForLogin';
import OriginArticle from './OriginArticle';
import CommentDetail  from './CommentDetail';
import TextEditor from '../TextEditor/Draft';
import './style/comment.css';

class Comment extends Component {
  constructor(props) {
    super(props);
  }

  whichHeader(){
    if(cookie.get('loginState') == "1")
      return ( <HeaderForLogin menu={["100"]} /> );
    else return ( <Header menu={["100"]}/> )
  }

  changeActionsRequest( url, commentId ){
     if ( cookie.get('loginState') == "1"  ){
        axios.post( url,
        {
       		articleId: commentId,
       		userId: cookie.get('userId'),
   	    })
         .then( res => {
            if( res.status === 200){
                if( res.data.code == 0){
                  // console.log("changeActionsRequestRes", res.data);
                  this.getComment("http://127.0.0.1:8080");
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

  render(){
    console.log("this.props.match.params", this.props.match.params);
    return(
      <div className="comment">
         { this.whichHeader() }
         <div className="comment-content">
             <OriginArticle
                 articleId={ this.props.match.params.id }
                 likeThis={ this.props.match.params.likeOrNot }
                 replyThis={ this.props.match.params.replyOrNot }
             />
             <CommentDetail
                 articleId={ this.props.match.params.id }
             />

         </div>
      </div>
    );
  }
}

function mapStateToProps( state ){
   return {
     loginState: state.login,
    };
}

export default connect( mapStateToProps, null)(Comment);
