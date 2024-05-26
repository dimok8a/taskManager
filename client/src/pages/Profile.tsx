import React, {useEffect, useRef} from 'react';
import {useAppDispatch, useAppSelector, useFetch, useForm} from "../store/hooks";
import {ParticipantData, ParticipantDataSent, TaskDataReceived, TaskDataToAddSent} from "../http/projectResponseTypes";
import {changeUserData, fetchTask, fetchUserData, logout} from "../http/projectRequestFunctions";
import {clearUser, setUser} from "../store/userSlice";
import {useNavigate} from "react-router-dom";
import {LOGIN_ROUTE} from "../consts";

const Profile = () => {
    const user = useAppSelector((state) => state.user);
    const dispatch = useAppDispatch();
    const {data, loading, error} = useFetch<ParticipantData>(fetchUserData());
    const { values, handleChange, resetForm, setValues } = useForm<ParticipantDataSent>({firstname: "", lastname:"" });
    const statusRef = useRef<HTMLDivElement>(null)
    const navigate = useNavigate();
    useEffect(() => {
        if (data) {
            setValues({
                ...values,
                lastname: data.lastname,
                firstname: data.firstname,
            })
        }
    }, [data]);

    const onSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        changeUserData(values).then(() => {
            statusRef.current!.textContent = "Данные успешно сохранены"
        })
    }

    const onLogout = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        logout().then(() => {
            dispatch(clearUser())
            navigate(LOGIN_ROUTE)
        })
    }

    return (
        <div className="section">
            <section className="rl_section_contact1">
                <div className="rl-padding-global">
                    <div className="rl-container-small">
                        <div className="rl-padding-section-large">
                            <div className="rl_contact1_component">
                                <div className="rl_contact1_heading-wrapper">
                                    <div className="rl_contact1_spacing-block-1"></div>
                                    <h2 className="rl-heading-style-h2">Изменить личные данные</h2>
                                </div>
                                <div className="rl_contact1_spacing-block-3"></div>
                                <div className="rl_contact1_form-block w-form">
                                    <form
                                        id="wf-form-Contact-1-Form"
                                        name="wf-form-Contact-1-Form"
                                        data-name="Contact 1 Form"
                                        method="get"
                                        className="rl_contact1_form"
                                        data-wf-page-id="664726f552d9ad8bc9e6aa50"
                                        data-wf-element-id="879225cd-e7de-880f-b2e8-b5bbb2910296"
                                    >
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Name-4" className="rl-field-label">Имя</label>
                                            <input className="rl-form-input w-input"
                                                   value={values.firstname}
                                                   onChange={handleChange}
                                                   name="firstname" data-name="Contact 1 Name 4"
                                                   placeholder="" type="text"/>
                                        </div>
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Email-4"
                                                   className="rl-field-label">Фамилия</label>
                                            <input className="rl-form-input w-input"
                                                   value={values.lastname}
                                                   onChange={handleChange}
                                                   name="lastname" data-name="Contact 1 Email 4"
                                                   placeholder="" type="text"/>
                                        </div>
                                        {/*<div id="w-node-_853ec994-b2c1-282e-e293-e2f96786c81f-c9e6aa50"*/}
                                        {/*     className="rl_contact1_form-field-wrapper">*/}
                                        {/*    <label htmlFor="Contact-1-Email-4" className="rl-field-label">Старый*/}
                                        {/*        пароль</label>*/}
                                        {/*    <input className="rl-form-input w-input"*/}
                                        {/*           value={values.oldPassword}*/}
                                        {/*           onChange={handleChange}*/}
                                        {/*           name="oldPassword" data-name="Contact 1 Email 4"*/}
                                        {/*           placeholder="" type="password"*/}
                                        {/*           />*/}
                                        {/*</div>*/}
                                        {/*<div id="w-node-ff95b359-c4a5-7d17-d0a8-20599de5f36c-c9e6aa50"*/}
                                        {/*     className="rl_contact1_form-field-wrapper">*/}
                                        {/*    <label htmlFor="Contact-1-Email-4" className="rl-field-label">Новый*/}
                                        {/*        пароль</label>*/}
                                        {/*    <input className="rl-form-input w-input"*/}
                                        {/*           value={values.newPassword}*/}
                                        {/*           onChange={handleChange}*/}
                                        {/*           name="newPassword" data-name="Contact 1 Email 4"*/}
                                        {/*           placeholder="" type="password"*/}
                                        {/*           />*/}
                                        {/*</div>*/}
                                        <div id="w-node-_879225cd-e7de-880f-b2e8-b5bbb291029f-c9e6aa50"
                                             className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button
                                                onClick={onSave}
                                                id="w-node-_879225cd-e7de-880f-b2e8-b5bbb29102a1-c9e6aa50"
                                                className="rl-button w-button">Сохранить изменения
                                            </button>
                                        </div>
                                        <div ref={statusRef}></div>
                                    </form>
                                    <button
                                        onClick={onLogout}
                                        id="w-node-_879225cd-e7de-880f-b2e8-b5bbb29102a1-c9e6aa50"
                                        className="rl-button w-button">Выйти
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Profile;
