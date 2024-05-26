import React, {useEffect} from 'react';
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import AppRouter from "./components/AppRouter";
import {useDispatch} from "react-redux";
import {clearUser, setUser} from "./store/userSlice";
import "./assets/css/styles.css"
import {fetchUserData} from "./http/projectRequestFunctions";

const App: React.FC = () => {
    const dispatch = useDispatch();

    useEffect(() => {
        fetchUserData()()
            .then(() => {
            const userData = localStorage.getItem('user');
            if (userData) {
                dispatch(setUser(JSON.parse(userData)));
            }
        })
            .catch(() => {
                dispatch(clearUser());
            })
    }, [dispatch]);
  return (
      <BrowserRouter>
          <AppRouter/>
      </BrowserRouter>
  );
};


export default App;
