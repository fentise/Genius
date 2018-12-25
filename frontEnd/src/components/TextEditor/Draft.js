import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import BlockStyleControls from './Block';
import InlineStyleControls from './Inline';
import Immutable from 'immutable';
import axios from 'axios';
import { stateToHTML } from 'draft-js-export-html';
import SubmitButton from './SubmitButton';
import { cookie }  from '../cookie/cookie';
import './style/Draft.css';
import './style/SubmitButton.css';

//import { Editor, EditorState, RichUtils } from 'draft-js'; //RichUtils:渲染工具
import {
    convertFromRaw,
    convertToRaw,
    CompositeDecorator,
    DefaultDraftBlockRenderMap,
    ContentState,
    Editor,
    EditorState,
    Entity,
    RichUtils,
    getDefaultKeyBinding,
    KeyBindingUtil,
    Modifier,
    SelectionState
} from 'draft-js';

      var backdraft = require('backdraft-js');
      class TextEditor extends React.Component {
        constructor(props) {
          super(props);
          this.state = {
            editorState: EditorState.createEmpty(),
            courseId: this.props.courseId,
            html: ""
          };
          this.onChange = (editorState)=>{
            this.setState({
              editorState
            });
          }

          this.focus = () => this.refs.editor.focus();
          this.handleKeyCommand = this._handleKeyCommand.bind(this);
          this.mapKeyToEditorCommand = this._mapKeyToEditorCommand.bind(this);
          this.toggleBlockType = this._toggleBlockType.bind(this);
          this.toggleInlineStyle = this._toggleInlineStyle.bind(this);
        }

        //将内容转换成HTML格式
        convertToHTMLAndEncode () {
          var contentState = this.state.editorState.getCurrentContent();
          var raw =  convertToRaw(contentState);
          var html = stateToHTML(contentState);
          //这里要用encodeURIComponent处理，否则换行会导致评论失败
          //不能使用encodeURI，否则所有内容都合并到第一行
          // var encode = encodeURIComponent(html);
          // document.body.innerHTML.replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi,'').replace(/<[^>]+?>/g,'').replace(/\s+/g,' ').replace(/ /g,' ').replace(/>/g,' ');
          return html;
        }

        convertToRaw()
         {
             var contentState = this.state.editorState.getCurrentContent();
             const plainText = contentState.getPlainText();
             console.log( "plainText" ,plainText);
             return plainText ;
        }

        submit( url ){
          if ( cookie.get('loginState')  == "1" )
            {
              return axios.post(url, {
                articleId: parseInt(this.props.articleId),
                content: this.convertToHTMLAndEncode(),
                rawContent: this.convertToRaw(),
                userId: cookie.get('userId'),

                }
             )
            .then( res => {
              if ( res.status == 200 ) {

                //调用父类函数，获取新的讨论数据
                  this.props.getNewReply();
                //如果评论成功，则清空编辑器
                if(res.data.message="操作成功。" ){
                  this.setState({
                    editorState: EditorState.createEmpty()
                  });
                }
                return res;
              }
            })
            .catch( error => {
              alert('评论失败');
            });
          }
          else alert("您尚未登录，请登录后再操作！");
        }


        //获取输入字数
        getWordsNumber()  {
           var contentState = this.state.editorState.getCurrentContent();
           var plainText = contentState.getPlainText();
           return plainText.length;
        }

        _handleKeyCommand(command, editorState) {
          const newState = RichUtils.handleKeyCommand(editorState, command);
          if (newState) {
            this.onChange(newState);
            return true;
          }
          return false;
        }

        _mapKeyToEditorCommand(e) {
          if (e.keyCode === 9 /* TAB */) {
            const newEditorState = RichUtils.onTab(
              e,
              this.state.editorState,
              4, /* maxDepth */
            );
            if (newEditorState !== this.state.editorState) {
              this.onChange(newEditorState);
            }
            return ;
          }
          return getDefaultKeyBinding(e);
        }

        _toggleBlockType(blockType) {
          this.onChange(
            RichUtils.toggleBlockType(
              this.state.editorState,
              blockType
            )
          );
        }

        _toggleInlineStyle(inlineStyle) {
          this.onChange(
            RichUtils.toggleInlineStyle(
              this.state.editorState,
              inlineStyle
            )
          );
        }

        render() {
          const {editorState} = this.state;
          let className = 'RichEditor-editor';
          const SubmitURL = "http://127.0.0.1:8080/addComment";
          // if (!contentState.hasText()) {
          //   if (contentState.getBlockMap().first().getType() !== 'unstyled') {
          //     className += ' RichEditor-hidePlaceholder';
          //   }
          // }
          return (
          <div className="RichEditor">
            <div className="RichEditor-root">
              <BlockStyleControls
                editorState={editorState}
                onToggle={this.toggleBlockType}
              />
              <InlineStyleControls
                editorState={editorState}
                onToggle={this.toggleInlineStyle}
              />
              <div className={className} onClick={this.focus}>
                <Editor
                  editorState={editorState}                        //
                  onChange={this.onChange}                         //
                  blockStyleFn={getBlockStyle}                     //

                  customStyleMap={styleMap}                        //
                  handleKeyCommand={this.handleKeyCommand}         //

                  keyBindingFn={this.mapKeyToEditorCommand}        //

                  placeholder="请输入评论..."                    //初始语句
                  ref="editor"                                     //
                  spellCheck={true}                                //拼写检查
                />
              </div>
            </div>
            <div className="RichEditor-footer">
               当前已输入{ this.getWordsNumber() }个字符, 您还可以输入{ 10000-this.getWordsNumber() }个字符。
            </div>
            <div className="RichEditor-button">
              <SubmitButton
                 courseId={ this.props.courseId }
                 submitContent={ this.submit.bind(this, SubmitURL) }
              />
            </div>
          </div>
          );
        }
      }

      // Custom overrides for "code" style.
        const styleMap = {
          CODE: {
            backgroundColor: 'rgba(0, 0, 0, 0.05)',
            fontFamily: '"Inconsolata", "Menlo", "Consolas", monospace',
            fontSize: 16,
            padding: 2,
          },
        };

        function getBlockStyle(block) {
          switch (block.getType()) {
            case 'blockquote':   return 'RichEditor-blockquote';
            default: return null;
          }
        }

  export default TextEditor;
  // export htmlContent;
