import * as React from 'react';
import * as styles from './TopBar.css';

interface Props {

}

export default class TopBar extends React.PureComponent<Props> {
    render() {
        return (
            <div className={styles.topBar}>
                <div className={styles.title}>Flip Flag</div>
            </div>
        );
    }
}
