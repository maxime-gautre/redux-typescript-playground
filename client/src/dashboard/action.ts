import axios from 'axios';
import { createStandardAction } from 'typesafe-actions';

axios.defaults.baseURL = 'http://localhost:9000';

export const incrementCounter = createStandardAction('INCREMENT_COUNTER')<void>();
