import axios from 'axios';
import { LOGIN, REGISTER } from './type';

export const loginRequest = (data , history ) => async dispatch =>{
  return axios.post( "/",
    {
      userName: data.userName,
      password: data.password,
      // rememberMe: "", //待定
    }
  //   {
  //     userid: "",
  //     result: success / failde
  //     message: "success"/"WrongPassword"/"NoUser"
  //   }
  )
  .then( res => {
    if ( res.status === 200 ){
      console.log("res:", res);
      alert("登录成功！");
      history.replace('/userInfo');
      return dispatch({
        type: LOGIN,
        payload: res.data
      });
    }
  })
  .catch( error => {
     // console.log("data", data);
     console.log("error:", error);
     if( error.data.message = "WrongPassword")
     alert("密码错误！");
     if( error.data.message = "NoUser")
     alert("账号不存在！");
  });
}


export const registerRequest = (data , history ) => async dispatch =>{
    history.replace('/');
  // return axios.post( "/",
  //   {
  //     email: data.email,
  //     userName: data.userName,
  //     password: data.password,
  //   }
  // //   {
  // //     userid: "",
  // //     result: success / failde
  // //     message: "success"/"WrongPassword"/"NoUser"
  // //   }
  // )
  // .then( res => {
  //   if ( res.status === 200 ){
  //     console.log("res:", res);
  //     alert("注册成功！");
  //     history.replace('/userInfo');
  //     return dispatch({
  //       type: REGISTER,
  //       payload: res.data
  //     });
  //   }
  // })
  // .catch( error => {
  //    // console.log("data", data);
  //    console.log("error:", error);
  // });
}
