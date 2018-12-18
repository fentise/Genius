import { LOGIN } from '../actions/type';

export default function (state = { data: "emptyData" }, action){
  switch (action.type) {
    case LOGIN:
      return action.payload;
    default:
      return state;
  }
}
