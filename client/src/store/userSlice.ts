// src/store/userSlice.ts
import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface UserState {
    id: string | null;
    token: string;
}

const initialState: UserState = {
    id: null,
    token: ''
};

const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<UserState>) => {
            state.id = action.payload.id;
            state.token = action.payload.token;
        },
        clearUser: (state) => {
            state.id = null;
            state.token = '';
        }
    }
});

export const { setUser, clearUser } = userSlice.actions;

export default userSlice.reducer;
