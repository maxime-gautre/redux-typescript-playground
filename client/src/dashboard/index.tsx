import * as React from 'react';
import { GlobalState } from '../store';
import { connect } from 'react-redux';

import * as styles from './style.css';
import TopBar from '../commons/components/TopBar';

interface Props {

}

export class App extends React.PureComponent<Props> {
    render() {
        return (
            <div>
                <TopBar/>
                <div className={styles.mainContainer}>
                    Hello world
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state: GlobalState): Props => {
    return {};
};

export default connect(mapStateToProps)(App);
