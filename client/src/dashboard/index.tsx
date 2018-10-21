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
    fetchFlips: () => void;
}

type Props = DispatchProps & DashBoardState;

export class App extends React.PureComponent<Props> {
    render() {
        return (
            <div>
                <TopBar/>
                <div className={styles.mainContainer}>
                    <div>Hello world {this.props.counter}</div>
                    <button onClick={() => this.props.fetchFlips()}>My Button</button>
                    <ul>
                        {this.props.flips.map(flip => {
                            return <li key={flip.id}>{flip.name}</li>;
                        })}
                    </ul>
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
        incrementCounter: () => dispatch(featureFlipAction.incrementCounter()),
        fetchFlips: () => {
            return dispatch(featureFlipAction.initFetchFlip());
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(App);
