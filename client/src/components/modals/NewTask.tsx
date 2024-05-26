import React, {useEffect, useRef} from 'react';
import {ModalParams} from "./modalParams";
import ModalWrapper from "./modalWrapper";
import {useForm} from "../../store/hooks";
import {ParticipantData, TaskDataToAddSent} from "../../http/projectResponseTypes";
import {createTask} from "../../http/projectRequestFunctions";

const NewTask: React.FC<{ modalParams: ModalParams, boardId: string, participants: ParticipantData[] }> = ({modalParams, boardId, participants}) => {
    const { values, handleChange, resetForm, setValues } = useForm<TaskDataToAddSent>({ boardId, name: '', description: '', executorId: "", inspectorId: "" });
    const {show, onHide} = modalParams
    const formRef = useRef<HTMLFormElement>(null)
    const onCreate = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        if (formRef.current?.checkValidity()) {
            createTask(values).then(() => {
                resetForm()
                onHide()
            })
        } else {
            formRef.current?.reportValidity();
        }
    }

    useEffect(() => {
        setValues(prevValues => ({
            ...prevValues,
            boardId
        }));
    }, [boardId]);

    return (
        <ModalWrapper modalParams={{show, onHide}}>
            <section className="rl_section_contact1">
                <div className="rl-padding-global">
                    <div className="rl-container-small">
                        <div className="rl-padding-section-large">
                            <div className="rl_contact1_component">
                                <div className="rl_contact1_heading-wrapper">
                                    <div className="rl_contact1_spacing-block-1"></div>
                                    <h2 className="rl-heading-style-h2">Новая задача</h2>
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
                                        data-wf-page-id="664726f552d9ad8bc9e6aa60"
                                        data-wf-element-id="63158e05-966f-4bf9-e25d-e5e56984d915"
                                    >
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Name-3"
                                                   className="rl-field-label">Название</label>
                                            <input className="rl-form-input w-input"
                                                   required={true}
                                                   onChange={handleChange}
                                                   name="name" data-name="Contact 1 Name" placeholder=""
                                                   type="text" id="Contact-1-Name-3"/>
                                        </div>
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Email-3"
                                                   className="rl-field-label">Описание</label>
                                            <input className="rl-form-input w-input"
                                                   onChange={handleChange}
                                                   name="description" data-name="Contact 1 Email" placeholder=""
                                                   type="text" id="Contact-1-Email-3"/>
                                        </div>
                                        <div id="w-node-dcf49623-7454-d44f-44e4-1b61bed7430a-c9e6aa60"
                                             className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="field" className="rl-field-label">Исполнитель</label>
                                            <select id="field" name="executorId" data-name="Field"
                                                    onChange={handleChange}
                                                    className="select-field w-select">
                                                <option value="">Выберите исполнителя</option>
                                                {participants.map(participant =>
                                                    <option key={participant.id} value={participant.id}>{participant.firstname + " " + participant.lastname}</option>
                                                )}
                                            </select>
                                        </div>
                                        <div id="w-node-f944fc00-dd0a-ef97-cfb5-2de7cb8c2564-c9e6aa60"
                                             className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="field-2" className="rl-field-label">Проверяющий</label>
                                            <select id="field-2" name="inspectorId" data-name="Field 2"
                                                    onChange={handleChange}
                                                    className="select-field w-select">
                                                <option value="">Выберите проверяющего</option>
                                                {participants.map(participant =>
                                                    <option key={participant.id} value={participant.id}>{participant.firstname + " " + participant.lastname}</option>
                                                )}
                                            </select>
                                        </div>
                                        <div id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d928-c9e6aa60"
                                             className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button onClick={onCreate}
                                                    type={"submit"}
                                                   id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
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

export default NewTask;
