import { LOGIN } from '../actions/type';
// {
//   userId: "",   //用户Id
//   result: 1/0,  //成功为1，失败为0
//   message: "success"/ "WrongPassword" /"NoUser"   // 登录成功 / 密码错误 / 用户不存在
// }
export default function (state={ userId:"52", result: 0, message:"NoUser", userName: "testUser" }, action){
  switch (action.type) {
    case LOGIN:
      return action.payload;
    default:
      return state;
  }
}
