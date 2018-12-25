

import React, {Component} from 'react';
import StyleButton from './StyleButton';
import './style/Draft.css';

var INLINE_STYLES = [
      {label: 'B', style: 'BOLD'},
      {label: 'I', style: 'ITALIC'},
      {label: 'U', style: 'UNDERLINE'},
    ];

    const InlineStyleControls = (props) => {
      const currentStyle = props.editorState.getCurrentInlineStyle();

      return (
        <div className="RichEditor-controls">
          {
            INLINE_STYLES.map((type) =>
            <StyleButton
              key={type.label}
              active={currentStyle.has(type.style)}
              label={type.label}
              onToggle={props.onToggle}
              style={type.style}
            />
          )}
        </div>
      );
    };

export default InlineStyleControls;
