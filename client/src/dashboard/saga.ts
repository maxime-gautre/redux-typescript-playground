import { all, call, put, takeLatest } from 'redux-saga/effects';
import { fetchFlipFailure, fetchFlipSuccess } from './action';
import axios from 'axios';
import { SagaIterator } from 'redux-saga';
import * as featureFlipAction from './action';
import { getType } from 'typesafe-actions';

axios.defaults.baseURL = 'http://localhost:9000';

const getFlips = () =>
    axios.get('/api/flips')
        .then(response => response.data)
        .catch(err => {
            throw err;
        });


function* fetchData() {
    try {
        const data = yield call(getFlips);
        yield put(fetchFlipSuccess(data));
    } catch (e) {
        yield put(fetchFlipFailure());
    }
}

function* dashboardSaga(): SagaIterator {
    yield all([
        takeLatest(getType(featureFlipAction.initFetchFlip), fetchData),
    ]);
}

export default dashboardSaga;
