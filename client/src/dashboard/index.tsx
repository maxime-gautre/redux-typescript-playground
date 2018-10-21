import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';

import { GlobalState } from '../store';
import * as featureFlipAction from './action';
import { DashBoardState } from './reducer';
import TopBar from '../commons/components/TopBar';

import * as styles from './style.css';

interface DispatchProps {
    incrementCounter: () => void;
}

type Props = DispatchProps & DashBoardState;

export class App extends React.PureComponent<Props> {

    render() {
        return (
            <div>
                <TopBar/>
                <div className={styles.mainContainer}>
                    <div>Hello world {this.props.counter}</div>
                    <button onClick={() => this.props.incrementCounter()}>My Button</button>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state: GlobalState): DashBoardState => {
    return state.dashboardState;
};

const mapDispatchToProps = (dispatch: Dispatch): DispatchProps => {
    return {
        incrementCounter: () => dispatch(featureFlipAction.incrementCounter())
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
