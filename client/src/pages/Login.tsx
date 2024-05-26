import React, {FormEvent, useRef} from 'react';
import {useForm, useAppDispatch, useAppSelector} from "../store/hooks";
import {setUser, UserState} from "../store/userSlice";
import {Link, useNavigate} from "react-router-dom";
import {login} from "../http/projectRequestFunctions";
import {UserLoginDataSent} from "../http/projectResponseTypes";
import md5 from "md5";
import {randomInt} from "node:crypto";
import {PROJECTS_ROUTE} from "../consts";

const Login = () => {
    const user = useAppSelector((state) => state.user);
    const dispatch = useAppDispatch();

    const { values, handleChange, resetForm } = useForm({ nickname: '', password: '' });
    const navigate = useNavigate();
    const statusRef = useRef<HTMLDivElement>(null)


    const handleLogin = (event: FormEvent) => {
        event.preventDefault();
        const rnd = Math.floor(Math.random() * 5000);
        const userLoginDataSent: UserLoginDataSent = {
            hash: md5(md5(values.nickname + values.password) + rnd.toString()),
            nickname: values.nickname,
            rnd: rnd.toString()
        }
        login(userLoginDataSent).then(result => {
            dispatch(setUser({
                id: result.id,
                token: result.accessToken
            }));
            navigate(PROJECTS_ROUTE)
        })
            .catch((err) => {
                statusRef.current!.textContent = err.response.data.message
            })
        // const userData: UserState = {
        //     id: '1',
        //     token: '1sdfsdxcvzxcv',
        // };
        // dispatch(setUser(userData));
    };

    return (
        <div className="w-layout-blockcontainer udesly-container w-container">
            <div className="udesly-form-block udesly-sign-in w-form">
                <div id="wf-form-Email-Form" data-name="Email Form" className="udesly-form-sign-in" data-wf-page-id="664726f552d9ad8bc9e6aa52" data-wf-element-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d4">
                    <input
                        className="udesly-udesly-text-field udesly-mb-16 w-input"
                        name="nickname"
                        data-name="Nickname 2"
                        placeholder="Никнейм"
                        data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d5"
                        type="text"
                        id="nickname-2"
                        onChange={handleChange}
                    />
                    <div className="udesly-password">
                        <input
                            className="udesly-text-field-password w-input"
                            name="password"
                            data-name="Password 2"
                            placeholder="Пароль"
                            data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d7"
                            type="password"
                            id="Password-2"
                            onChange={handleChange}
                        />
                        <img loading="lazy" src="https://assets-global.website-files.com/664726f552d9ad8bc9e6a9c2/6649a9391f7a51899d9f9cb8_Show.svg" alt="" className="udesly-password-image" />
                    </div>
                    <div className="udesly-button-wrapper udesly-mb-16">
                        <input
                            onClick={handleLogin}
                            type="submit" data-wait="Please wait..." data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0da" className="udesly-submit-button udesly-width-full w-button" value="Войти"
                        />
                    </div>
                    <Link to="/registration" data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0db" className="udesly-link-sign-in">Еще нет аккаунта? Пройдите регистрацию</Link>
                </div>
                <div ref={statusRef}></div>
            </div>
        </div>
    );
};

export default Login;
