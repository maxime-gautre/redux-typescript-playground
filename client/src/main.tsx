import 'es6-promise/auto';
import 'es6-shim';
import 'reset-css/reset.css';
import './main.css';
import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { Route, Router, Switch } from 'react-router';
import { Provider } from 'react-redux';
import Dashboard from './dashboard';
import { store, history } from './store';

ReactDOM.render(
    <Provider store={store}>
        <Router history={history}>
            <div>
                <Switch>
                    <Route exact path="/" component={Dashboard}/>
                </Switch>
            </div>
        </Router>
    </Provider>,
    document.getElementById('app')
);
