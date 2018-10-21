import { action, createStandardAction } from 'typesafe-actions';
import { FeatureFlip } from './reducer';

export const incrementCounter = createStandardAction('INCREMENT_COUNTER')<void>();
export const initFetchFlip = () => action('INIT_FETCH_FLIPS');
export const fetchFlipSuccess = (data: FeatureFlip[]) => action('FLIPS_FETCHED', data);
export const fetchFlipFailure = () => action('FLIPS_FAILURE');
