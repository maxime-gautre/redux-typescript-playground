import { Reducer } from 'redux';
import { ActionType, getType } from 'typesafe-actions';
import * as featureFlipActions from './action';

export interface DashBoardState {
    counter: number;
    flips: FeatureFlip[];
}

export interface FeatureFlip {
    id: number;
    name: string;
    activated: boolean;
}

const initialState: DashBoardState = {
    counter: 0,
    flips: [],
};

export type FeatureFlipAction = ActionType<typeof featureFlipActions>;

const reducer: Reducer<DashBoardState> = (state = initialState, action: FeatureFlipAction) => {
    switch (action.type) {
        case getType(featureFlipActions.incrementCounter): {
            return { ...state, counter: state.counter + 1 };
        }
        case getType(featureFlipActions.fetchFlipSuccess): {
            return { ...state, flips: action.payload };
        }
        default: {
            return state;
        }
    }
};

export { reducer as dashboardReducer };
