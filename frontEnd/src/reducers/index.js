import { combineReducers } from 'redux';
import loginReducer from  './loginReducer';
import registerStateReducer from './registerStateReducer';

const rootReducer = combineReducers({
   login: loginReducer,
   registerState: registerStateReducer,
});

export default rootReducer;
