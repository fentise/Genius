import React,{ Component } from 'react';

import HeaderForLogin from '../header/HeaderForLogin';
import WriteArticleDetail from './WriteArticleDetail';
import './style/writeArticle.css';


class WriteArticle extends Component {
  constructor(props){
    super(props);
  }
render(){
    return(
      <div className="writeArticle">
         <HeaderForLogin  menu={['3']}/>
         <WriteArticleDetail
           history={ this.props.history }/>
      </div>
    );
  }
}

export default WriteArticle;
