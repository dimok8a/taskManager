import React, {useEffect, useRef} from 'react';
import {ModalParams} from "./modalParams";
import ModalWrapper from "./modalWrapper";
import {useForm} from "../../store/hooks";
import {BoardDataSent, ParticipantData, TaskDataToAddSent} from "../../http/projectResponseTypes";
import {createBoard, createTask} from "../../http/projectRequestFunctions";

const NewBoard: React.FC<{ modalParams: ModalParams, projectId: string, participants: ParticipantData[] }> = ({modalParams, projectId, participants}) => {
    const {show, onHide} = modalParams
    const { values, handleChange, resetForm, setValues } = useForm<BoardDataSent>({ projectId, name: '', description: '', inspectorId: "" });
    const formRef = useRef<HTMLFormElement>(null)

    const onCreate = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        if (formRef.current?.checkValidity()) {
            createBoard(values).then(() => onHide())
            resetForm()
        } else {
            formRef.current?.reportValidity();
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
                                        <h2 className="rl-heading-style-h2">Новая доска</h2>
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
                                                       className="rl-field-label">Название</label>
                                                <input className="rl-form-input w-input"
                                                       required={true}
                                                       onChange={handleChange}
                                                       name="name" data-name="Contact 1 Name 4" placeholder=""
                                                       type="text" id="Contact-1-Name-4"/>
                                            </div>
                                            <div className="rl_contact1_form-field-wrapper">
                                                <label htmlFor="Contact-1-Email-4"
                                                       className="rl-field-label">Описание</label>
                                                <input className="rl-form-input w-input"
                                                       onChange={handleChange}
                                                       name="description" data-name="Contact 1 Email 4" placeholder=""
                                                       type="text" id="Contact-1-Email-4"/>
                                            </div>
                                            <div id="w-node-b1bd95e1-84db-0807-8186-1ed33e5131e3-c9e6aa59"
                                                 className="rl_contact1_form-field-wrapper">
                                                <label htmlFor="field-3" className="rl-field-label">Проверяющий по
                                                    умолчанию</label>
                                                <select id="field-2" name="inspectorId" data-name="Field 2"
                                                        onChange={handleChange}
                                                        className="select-field w-select">
                                                    <option value="">Выберите проверяющего по умолчанию</option>
                                                    {participants.map(participant =>
                                                        <option key={participant.id} value={participant.id}>{participant.firstname + " " + participant.lastname}</option>
                                                    )}
                                                </select>
                                            </div>
                                            <div id="w-node-b1bd95e1-84db-0807-8186-1ed33e5131e7-c9e6aa59"
                                                 className="rl_contact1_button-wrapper">
                                                <div className="rl_contact1_spacing-block-4"></div>
                                                <button
                                                       onClick={onCreate}
                                                       id="w-node-b1bd95e1-84db-0807-8186-1ed33e5131e9-c9e6aa59"
                                                       className="rl-button w-button">Создать</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
        </ModalWrapper>
    );
};

export default NewBoard;
