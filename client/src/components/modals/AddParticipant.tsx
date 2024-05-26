import React, {useEffect, useRef} from 'react';
import {ModalParams} from "./modalParams";
import ModalWrapper from "./modalWrapper";
import {useForm} from "../../store/hooks";
import {
    AddUserToProjectDataSent,
    BoardDataSent,
    ParticipantData,
    TaskDataToAddSent
} from "../../http/projectResponseTypes";
import {addUserToProject, createBoard, createTask} from "../../http/projectRequestFunctions";

const AddParticipant: React.FC<{ modalParams: ModalParams, projectId: string }> = ({modalParams, projectId}) => {
    const { values, handleChange, resetForm, setValues } = useForm<AddUserToProjectDataSent>({ newUserNickname: "" });
    const formRef = useRef<HTMLFormElement>(null)
    const statusRef = useRef<HTMLDivElement>(null)

    const onAdd = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        if (formRef.current?.checkValidity()) {
            addUserToProject(values, projectId).then(() => {
                statusRef.current!.textContent = "Пользователь успешно добавлен"
            })
                .catch(() => {
                    statusRef.current!.textContent = "Произошла ошибка"
                })
            resetForm()
        } else {
            formRef.current?.reportValidity();
            statusRef.current!.textContent = "Не все поля заполнены!"
        }
    }
    return (
        <ModalWrapper modalParams={modalParams}>
            <section className="rl_section_contact1">
                <div className="rl-padding-global">
                    <div className="rl-container-small">
                        <div className="rl-padding-section-large">
                            <div className="rl_contact1_component">
                                <div className="rl_contact1_heading-wrapper">
                                    <div className="rl_contact1_spacing-block-1"></div>
                                    <h2 className="rl-heading-style-h2">Добавить участника</h2>
                                </div>
                                <div className="rl_contact1_spacing-block-3"></div>
                                <div className="rl_contact1_form-block w-form">
                                    <form
                                        ref={formRef}
                                        id="wf-form-Contact-1-Form"
                                        name="wf-form-Contact-1-Form"
                                        data-name="Contact 1 Form"
                                        method="get"
                                        className="rl_contact1_form"
                                        data-wf-page-id="664726f552d9ad8bc9e6aa59"
                                        data-wf-element-id="b1bd95e1-84db-0807-8186-1ed33e5131d6"
                                    >
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Name-4"
                                                   className="rl-field-label">Никнейм участника</label>
                                            <input className="rl-form-input w-input"
                                                   required={true}
                                                   // defaultValue={values.newUserNickname}
                                                   value={values.newUserNickname}
                                                   onChange={handleChange}
                                                   name="newUserNickname" data-name="Contact 1 Name 4" placeholder=""
                                                   type="text" id="Contact-1-Name-4"/>
                                        </div>
                                        <div id="w-node-b1bd95e1-84db-0807-8186-1ed33e5131e7-c9e6aa59"
                                             className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button
                                                onClick={onAdd}
                                                id="w-node-b1bd95e1-84db-0807-8186-1ed33e5131e9-c9e6aa59"
                                                className="rl-button w-button">Добавить</button>
                                        </div>
                                    </form>
                                </div>
                                <div ref={statusRef}></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </ModalWrapper>
    );
};

export default AddParticipant;
