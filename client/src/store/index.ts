// src/store/index.ts
import {configureStore, Middleware} from '@reduxjs/toolkit';
import userReducer from './userSlice';

const localStorageMiddleware: Middleware = store => next => action => {
    const result = next(action);
    const state = store.getState();

    // Check if user data is available in state and store it in local storage
    if (state.user) {
        localStorage.setItem('user', JSON.stringify(state.user));
    }

    return result;
};

export const store = configureStore({
    reducer: {
        user: userReducer
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware().concat(localStorageMiddleware)
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
