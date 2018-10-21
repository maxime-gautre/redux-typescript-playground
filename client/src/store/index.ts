import { applyMiddleware, combineReducers, createStore, Reducer } from 'redux';
import { routerMiddleware, routerReducer, RouterState } from 'react-router-redux';
import createSagaMiddleware from 'redux-saga';
import { composeWithDevTools } from 'redux-devtools-extension';
import createHashHistory from 'history/createHashHistory';
import { dashboardReducer, DashBoardState } from '../dashboard/reducer';
import dashboardSaga from '../dashboard/saga';
import { all } from 'redux-saga/effects';

export interface GlobalState {
    router: Reducer<RouterState>;
    dashboardState: DashBoardState;
}

export const history = createHashHistory();
const routeMiddleware = routerMiddleware(history);
const sagaMiddleware = createSagaMiddleware();

const reducers = combineReducers({
    router: routerReducer,
    dashboardState: dashboardReducer,
});
const enhancer = composeWithDevTools(
    applyMiddleware(routeMiddleware),
    applyMiddleware(sagaMiddleware),
);
const store = createStore(reducers, enhancer);


// todo type
function* rootSaga(): any {
    yield all([
        dashboardSaga(),
    ]);
}

sagaMiddleware.run(rootSaga);

export { store as store };
