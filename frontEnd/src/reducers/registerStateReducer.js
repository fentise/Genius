import { REGISTERSTATE } from '../actions/type';
// {
//   registerState: 1,
//   newEmail: data.email
// }
export default function (state={ registerState: 0, newEmail:"" }, action){
  switch (action.type) {
    case REGISTERSTATE:
      return action.payload;
    default:
      return state;
  }
}
