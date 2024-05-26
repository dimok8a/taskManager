import React, {FormEvent, useRef} from 'react';
import {useForm, useAppDispatch, useAppSelector} from "../store/hooks";
import {setUser, UserState} from "../store/userSlice";
import {Link, useNavigate} from "react-router-dom";
import Login from "./Login";
import {UserRegistrationData, UserRegistrationDataSent} from "../http/projectResponseTypes";
import {CHANGE_USER_DATA} from "../http/requests";
import {HOME_ROUTE, LOGIN_ROUTE, PROJECTS_ROUTE, REGISTRATION_ROUTE} from "../consts";
import {register} from "../http/projectRequestFunctions";
import md5 from "md5";

const Registration = () => {
    const dispatch = useAppDispatch();
    const { values, handleChange, resetForm } = useForm<UserRegistrationData>({
        password1: "", password2: "",
        lastname: "",
        nickname: '', firstname: "" });
    const navigate = useNavigate();

    const formRef = useRef<HTMLFormElement>(null)
    const statusRef = useRef<HTMLDivElement>(null)

    const handleRegistration = (event: FormEvent) => {
        event.preventDefault();
        if (formRef.current?.checkValidity()) {
            if (values.password1 !== values.password2) {
                return statusRef.current!.textContent = "Пароли должны совпадать!"
            }
            const userRegistrationDataSent: UserRegistrationDataSent = {
                firstname: values.firstname,
                hash: md5(values.nickname + values.password1),
                lastname: values.lastname,
                nickname: values.nickname
            }
            register(userRegistrationDataSent).then((result) => {
                dispatch(setUser({
                    id: result.id,
                    token: result.accessToken
                }));
                navigate(PROJECTS_ROUTE)
                return statusRef.current!.textContent = "Регистрация прошла успешно!"
            })
                .catch((err) => {
                    return statusRef.current!.textContent = err
                })
        } else {
            formRef.current?.reportValidity()
        }
        console.log('Nickname:', values.nickname);
        console.log('Password:', values.password1);
        console.log('Password:', values.password2);
        // TODO
        const userData: UserState = {
            id: '1',
            token: '1sdfsdxcvzxcv',
        };
    };

    return (
        <div className="w-layout-blockcontainer udesly-container w-container">
            <div className="udesly-form-block udesly-sign-in w-form">
                <div id="wf-form-Email-Form" data-name="Email Form" className="udesly-form-sign-in"
                     data-wf-page-id="664726f552d9ad8bc9e6aa52"
                     data-wf-element-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d4">
                    <form ref={formRef}>
                        <input
                            className="udesly-udesly-text-field udesly-mb-16 w-input"
                            name="nickname"
                            data-name="Nickname 2"
                            placeholder="Никнейм"
                            data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d5"
                            type="text"
                            id="nickname-2"
                            onChange={handleChange}
                            required={true}
                        />
                        <input
                            className="udesly-udesly-text-field udesly-mb-16 w-input"
                            name="firstname"
                            data-name="Nickname 2"
                            placeholder="Имя"
                            data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d5"
                            type="text"
                            id="nickname-2"
                            onChange={handleChange}
                            required={true}
                        />
                        <input
                            className="udesly-udesly-text-field udesly-mb-16 w-input"
                            name="lastname"
                            data-name="Nickname 2"
                            placeholder="Фамилия"
                            data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d5"
                            type="text"
                            id="nickname-2"
                            onChange={handleChange}
                            required={true}
                        />
                        <div className="udesly-password">
                            <input
                                className="udesly-text-field-password w-input"
                                name="password1"
                                data-name="Password 2"
                                placeholder="Пароль"
                                data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d7"
                                type="password"
                                id="Password-2"
                                onChange={handleChange}
                                required={true}
                            />
                        </div>
                        <div className="udesly-password">
                            <input
                                className="udesly-text-field-password w-input"
                                name="password2"
                                data-name="Password 2"
                                placeholder="Повторите пароль"
                                data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0d7"
                                type="password"
                                id="Password-2"
                                onChange={handleChange}
                                required={true}
                            />
                        </div>
                        <div className="udesly-button-wrapper udesly-mb-16">
                            <button
                                onClick={handleRegistration}
                                data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0da"
                                className="udesly-submit-button udesly-width-full w-button">Создать аккаунт</button>
                        </div>
                    </form>
                    <div ref={statusRef}></div>
                    <Link to={LOGIN_ROUTE} data-w-id="13ad7141-bbfd-97f2-12fb-3dc5b5f4c0db"
                          className="udesly-link-sign-in">Уже зарегистрированы? Авторизуйтесь</Link>
                </div>
            </div>
        </div>
    );
};

export default Registration;
