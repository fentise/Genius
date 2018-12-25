import React,{ Component } from 'react';
import { Form, Input } from 'antd';

import TextEditor from './TextEditor/Draft';
import './style/writeArticleDetail.css';
const FormItem = Form.Item;
class WriteArticleDetailRaw extends Component {
  constructor(props){
    super(props);
  }
  state = {
    title: "",
    content: ""
  }


render(){
   const { getFieldDecorator } = this.props.form;
    return(
      <div className="writeArticleDetail">
          <h1 >发表帖子</h1>
          <div className="title">
          <Form layout="inline">
            <FormItem>
              {getFieldDecorator( 'Article title', {
                rules: [{ required: true, message: '请输入文章标题' },
                        {max: 60}],
              })(
                <Input placeholder="请输入文章标题" className="title" onChange={ event => this.setState({ title: event.target.value })}/>
              )}
            </FormItem>
           </Form>
          </div>
          <div className="editor">
             <TextEditor
               title={ this.state.title }
               history={ this.props.history }
             />
          </div>
      </div>
    );
  }
}
const WriteArticleDetail = Form.create()(WriteArticleDetailRaw);
export default WriteArticleDetail;
