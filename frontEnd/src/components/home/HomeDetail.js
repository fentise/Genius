import React,{ Component } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { connect } from 'react-redux';
import { Comment, Icon, Tooltip, Avatar, List, Tag } from 'antd';
import moment from 'moment';
import 'antd/dist/antd.css';

import { cookie }  from '../cookie/cookie';
import Header from '../header/Header';
import './style/home.css';
const GetCommentURL = "http://127.0.0.1:8080";
class HomeDetail extends Component {
  constructor(props){
    super(props);
  }

  componentWillMount(){
    this.getComment( GetCommentURL );
  }

  getComment( url ){
     return axios.post( url, {
               sortType: 0,
               themeType: 0,
               userId: cookie.get('userId'),
            })
            .then( res => {
             if(res.status == 200 )
               this.setState( { postCard: res.data.postCard,}
                );
             } )
            .catch( function(error){
              alert('获取数据失败');
              console.log(error);
           });
  }

  state = {
    postCard: [
      {
        public: {
          id: 123,               //该条帖子的Id
          author: "userName",    //发帖用户的用户名
          authorId: "authorId",  //发帖用户的Id
          avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',   //发帖的用户头像路径
          title: "标题",        //帖子的标题
          content: "回复内容回复内容回复内容回回复内容回复内容回复内容容回复内容回复内容回复内容", //帖子的内容
          datetime: "2018-11-16 11:16:26",  //发帖时间
          likeNum: 100,     // 该帖子的喜欢数
          replyNum: 123,    //该帖子的回复数
          storedNum: 48,  //该帖子的收藏数
          readNum: 156,
        },
        //已登录用户的关于该条评论的操作,如果用户未登录，则不需要myAcitons这部分
        myActions:{
         //storeThis: 0,   //该用户是否收藏了这个帖子,1已收藏, 0:未收藏
         likeThis: 1,      //该用户是否喜欢这个帖子,1:喜欢, 0:不喜欢
         repliedThis: 1,   //该用户是否回复了这个帖子,1:已回复, 0:未回复
        },
    },
      {
        public:{
          id: 124,
          author: "userName",
          avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
          title: "标题",
          content: "DataBase",
          datetime: "19:00 09.23",
          likeNum: 100,  // 点赞数
          replyNum: 123,
          storedNum: 4378,
          readNum: 136,
        },
        myActions:{
         likeThis: 1,  //该用户是否喜欢这个帖子,1:喜欢, 0:不喜欢
         storeThis: 1,   //该用户是否收藏了这个帖子,1已收藏, 0:未收藏
         repliedThis: 0,   //该用户是否回复了这个帖子,1:已回复, 0:未回复
        },
    },
      {
      public:{
        id: 125,
        author: "userName",
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        title: "标题",
        content: "DataBase",
        datetime: "19:00 09.23",
        likeNum: 100,  // 点赞数
        replyNum: 123,
        storedNum: 4378,
        readNum: 466,
      },
      myActions:{
       likeThis: 0,  //该用户是否喜欢这个帖子,1:喜欢, 0:不喜欢
       storeThis: 0,   //该用户是否收藏了这个帖子,1已收藏, 0:未收藏
       repliedThis: 0,   //该用户是否回复了这个帖子,1:已回复, 0:未回复
      },
   },
      {
      public:{
        id: 126,
        author: "userName",
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        title: "标题",
        content: "test",
        datetime: "23:00 10.03",
        likeNum: 100,  // 点赞数
        replyNum: 123,
        storedNum: 4378,
        readNum: 546,
      },
      myActions:{
       likeThis: 0,  //该用户是否喜欢这个帖子,1:喜欢, 0:不喜欢
       storeThis: 0,   //该用户是否收藏了这个帖子,1已收藏, 0:未收藏
       repliedThis: 1,   //该用户是否回复了这个帖子,1:已回复, 0:未回复
      },
  },
    ],
  }

  itemRender( current, type, originalElement ){
      if (type === 'prev') {
        return <a>Previous</a>;
      } if (type === 'next') {
        return <a>Next</a>;
      }
      return originalElement;
  }

  renderData( commentData, listData, loginState ){
      commentData.map( comment => listData.push({
        Id: comment.public.id,
        href: `comment/${comment.public.id}/${loginState?comment.myActions.likeThis:0}/${loginState?comment.myActions.repliedThis:0}`,
        commentId: comment.public.id,
        title: comment.public.title,
        avatar: comment.public.avatar,
        content: comment.public.content,
        datetime: comment.public.datetime,
        likeNum: comment.public.likeNum,
        replyNum: comment.public.replyNum,
        readNum: comment.public.readNum,

        likeThis: loginState?comment.myActions.likeThis:0,
        storeThis: loginState?comment.myActions.storeThis:0,
        repliedThis: loginState?comment.myActions.repliedThis:0,
      }) );
     return listData;
  }

  changeActionsRequest( url, commentId ){
     if ( cookie.get('loginState') ){
        axios.post( url,
        {
       		articleId: commentId,
       		userId: cookie.get('userId'),
   	    })
         .then( res => {
            if( res.status === 200){
                if( res.data.code == 0){
                  this.getComment( GetCommentURL );
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
     const LikeURL = "http://127.0.0.1:8080/likeArticle";
     const UnLikeURL = "http://127.0.0.1:8080/dislikeArticle";
     var finishedData  = [];
     finishedData = this.renderData( this.state.postCard, [], cookie.get('loginState'));
     finishedData.map( item =>{
         if( item.Id == id ){
             if(type == "like") {
                if( item.likeThis == 0 ){
                  this.changeActionsRequest(LikeURL,id); }
                else{
                  this.changeActionsRequest(UnLikeURL,id); }
             }
           }
         })
  }

  HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
  }
  converToComponent(content) {
                return  (
                  <div dangerouslySetInnerHTML={ {__html: this.HTMLDecode(content)} }></div>
                );
           }
  render(){
    var finishedData  = [];
    finishedData = this.renderData( this.state.postCard, [], cookie.get('loginState'));
    const IconText = ({ id, type, text, theme="outlined" }) =>(
        <span>
          <Icon type={type} style={{ marginRight: 8 }}  theme={ theme }
                onClick={ this.onClickAction.bind(this, id, type) } />
          {text}
        </span>
        )

    return(
        <div >
          <List
                 itemLayout="vertical"
                 size="large"
                 loading={  this.props.loadingState  }
                 pagination={{
                   pageSize: 10,
                   showQuickJumper: true,
                   showSizeChanger: false,
                   defaultCurrent: 6,
                   itemRender: this.itemRender(),
                 }}
                 dataSource={ finishedData }
                 renderItem={item => (
                 <List.Item
                   Id={ item.Id }
                   key={item.title}
                   actions={[
                             // <IconText id={ item.Id } type="star" text={ item.storedNum }
                             //    theme={ item.storeThis? 'filled' : 'outlined'} />,
                             <IconText id={ item.Id } type="like" text={ item.likeNum }
                                theme={ item.likeThis? 'filled' : 'outlined'} />,
                             <IconText id={ item.Id } type="message" text={ item.replyNum }
                                theme={ item.repliedThis? 'filled' : 'outlined'} />,
                             <IconText id={ item.Id } type="eye" text={ item.readNum }
                                theme={ 'outlined'} />
                           ]}
                  >
                      <List.Item.Meta
                        Id={ item.Id }
                        avatar={<Avatar src={item.avatar} />}
                        title={<Link to={ item.href } >{item.title}</Link>}
                      />
                       { this.converToComponent( item.content) }
                       <div className="home-content-itemTime">{item.datetime}</div>
                   </List.Item>
                  )}
             />
        </div>
      );
  }
}

function mapStateToProps( state ){
   return { loginState: state.login };
}

export default connect( mapStateToProps, null)(HomeDetail);
