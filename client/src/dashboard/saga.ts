import { all, call, put, takeLatest } from 'redux-saga/effects';
import { fetchFlipFailure, fetchFlipSuccess } from './action';
import axios from 'axios';
import { SagaIterator } from 'redux-saga';

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
        takeLatest('INIT_FETCH_FLIPS', fetchData),
    ]);
}

export default dashboardSaga;
