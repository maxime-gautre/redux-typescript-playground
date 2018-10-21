import { applyMiddleware, combineReducers, createStore } from 'redux';
import { routerMiddleware, routerReducer } from 'react-router-redux';
import createSagaMiddleware from 'redux-saga';
import { composeWithDevTools } from 'redux-devtools-extension';
import createHashHistory from 'history/createHashHistory';
import { appReducer, DashBoardState } from '../dashboard/reducer';

export interface GlobalState {
    dashboardState: DashBoardState;
}

export const history = createHashHistory();
const routeMiddleware = routerMiddleware(history);
const sagaMiddleware = createSagaMiddleware();

const reducers = combineReducers({
    router: routerReducer,
    appState: appReducer,
});
const enhancer = composeWithDevTools(
    applyMiddleware(routeMiddleware),
    applyMiddleware(sagaMiddleware),
);
export const store = createStore(reducers, enhancer);
