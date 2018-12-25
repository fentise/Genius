import axios from 'axios';
import { LOGIN, REGISTERSTATE } from './type';
import { cookie } from '../components/cookie/cookie';

export const loginRequest = ( url, data ) => async dispatch =>{
  return axios.post( url,
    {
      userEmail: data.userName,
      password: data.password,
    }
  )
  .then( res => {
    if ( res.status === 200 ){
      if( res.data.result === 1 )
      {
        console.log("Login: res.data:", res.data);
        cookie.delete('userName');
        cookie.set('userName', res.data.userName, 7);
        cookie.delete('userId');
        cookie.set('userId', res.data.userId,7);
        cookie.set('loginState', 1, 7 );
        alert("登录成功！");
        return dispatch({
          type: LOGIN,
          payload: res.data
        });
      }
      if( res.data.result === 0 )
      {
        switch (res.data.message) {
          case "WrongPassword":
                alert("密码错误，请重新输入");
                break;
          case "NoUser":
                alert("账号不存在，请先注册再登录");
         }
        return dispatch({
          type: LOGIN,
          payload: res.data
        });
      }
    }
  })
  .catch( error => {
     console.log("loginRequestError:", error);
     alert("登录失败，请检查网络！");
     // return dispatch({
     //     type: LOGIN,
     //     payload: {
     //       userId:"2", result:1, message:"failed"
     //    }
     // })
  })
}

export const registerRequest = ( url, data ) => async dispatch =>{
  return axios.post( url,
            {
              userEmail: data.email,
              userName: data.userName,
              password: data.password,
            }
          )
          .then( res => {
            if ( res.status === 200 ){
              switch ( res.data.message ){
                case "success":
                  console.log("cookieBefore", document.cookie);
                  alert("注册成功！即将前往登录...");
                  break;
                case "emailHasExit":
                  alert("该邮箱已经被注册，请选择另一个邮箱");
              }
              return dispatch({
                      type: REGISTERSTATE,
                      payload: {
                          registerState: 1,
                          newEmail: data.email
                          },
                    })
            }
          })
          .catch( error => {
             console.log("registerRequestError:", error);
             alert("注册失败，请检查网络！");
          });
}

export const restoreRegisterState = () => dispatch =>{
   return  dispatch({
               type: "",
               payload: {
                   registerState: 0,
                   newEmail: ""
                   },
            })
}

export const logOut = () => dispatch => {
  cookie.delete('loginState');
  cookie.set('loginState', 0 ,7);
  cookie.delete('userName');
  cookie.set('userName', "", 7);
  cookie.delete('userId');
  cookie.set('userId', "9999999", 7);
  if( cookie.get('remember') == "false" )
  {
    cookie.delete('userMail');
    cookie.delete('password');
    cookie.delete('remember');
  }
  return  dispatch({
              type: LOGIN,
              payload: { userId:"52", result: 0, message:"NoUser", userName: "testUser" }
           })
};
