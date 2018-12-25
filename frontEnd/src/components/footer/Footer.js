import React,{ Component } from 'react';
import { Link } from 'react-router-dom';

import './style/footer.css';

class Footer extends Component {
  constructor(props){
    super(props);
  }

  render(){
    return(
      <div className="footer">
        Footer
      </div>
    );
  }
}

export default Footer;
