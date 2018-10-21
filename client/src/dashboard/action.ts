import { createStandardAction } from 'typesafe-actions';
import { FeatureFlip } from './reducer';

export const incrementCounter = createStandardAction('INCREMENT_COUNTER')<void>();
export const initFetchFlip = createStandardAction('INIT_FETCH_FLIPS')<void>();
export const fetchFlipSuccess = createStandardAction('FLIPS_FETCHED')<FeatureFlip[]>();
export const fetchFlipFailure = createStandardAction('FLIPS_FAILURE')<void>();
