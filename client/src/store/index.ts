import { Reducer, createStore, combineReducers, applyMiddleware } from 'redux';
import { RouterState, routerReducer, routerMiddleware } from 'react-router-redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import createHashHistory from 'history/createHashHistory';

export interface GlobalState {
    router: Reducer<RouterState>;
}

export const history = createHashHistory();
const routeMiddleware = routerMiddleware(history);

const reducers = combineReducers({
    router: routerReducer,
});
const enhancer = composeWithDevTools(
    applyMiddleware(routeMiddleware),
);
export const store = createStore(reducers, enhancer);
