import { Reducer } from 'redux';
import { ActionType, getType } from 'typesafe-actions';
import * as featureFlipActions from './action';

export interface DashBoardState {
    counter: number;
}

const initialState: DashBoardState = {
    counter: 0
};

export type FeatureFlipAction = ActionType<typeof featureFlipActions>;

const reducer: Reducer<DashBoardState> = (state = initialState, action: FeatureFlipAction) => {
    switch (action.type) {
        case getType(featureFlipActions.incrementCounter): {
            return { ...state, counter: state.counter + 1 };
        }
        default: {
            return state;
        }
    }
};

export { reducer as appReducer };
