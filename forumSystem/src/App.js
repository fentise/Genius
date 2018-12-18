import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

import Home from './components/home/Home';
import Login from './components/login/Login';
import Register from './components/register/Register';
import UserInfo from './components/userInformation/UserInfo';
import UserAgreement from './components/userAgreement/UserAgreement';
class App extends Component {

  render() {
    return (
      <BrowserRouter>
        <div className="Router">
          <Switch>
            <Route path="/" component={ Home } exact  />
            <Route path="/login" component={ Login } />
            <Route path="/register" component={ Register } />
            <Route path="/userInfo" component={ UserInfo } />
            <Route path="/userAgreement" component={ UserAgreement } />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
