import React, {useRef} from 'react';
import {ModalParams} from "./modalParams";
import ModalWrapper from "./modalWrapper";
import {useForm} from "../../store/hooks";
import {BoardDataSent, ProjectDataSent} from "../../http/projectResponseTypes";
import {createBoard, createProject} from "../../http/projectRequestFunctions";

const NewProject: React.FC<ModalParams> = ({show, onHide}) =>  {
    const { values, handleChange, resetForm, setValues } = useForm<ProjectDataSent>({ name: "", description: "" });
    const formRef = useRef<HTMLFormElement>(null)

    const onCreate = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        if (formRef.current?.checkValidity()) {
            createProject(values).then(() => onHide())
            resetForm()
        } else {
            formRef.current?.reportValidity();
        }
    }
    return (
        <ModalWrapper modalParams={{show, onHide}}>
                <section className="rl_section_contact1">
                    <div className="rl-padding-global">
                        <div className="rl-container-small">
                            <div className="rl-padding-section-large">
                                <div className="rl_contact1_component">
                                    <div className="rl_contact1_heading-wrapper">
                                        <div className="rl_contact1_spacing-block-1"></div>
                                        <h2 className="rl-heading-style-h2">Новый проект</h2>
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
                                            data-wf-page-id="664726f552d9ad8bc9e6aa5a"
                                            data-wf-element-id="8d64cade-a67f-5204-5f63-d84654ad4320"
                                        >
                                            <div className="rl_contact1_form-field-wrapper">
                                                <label htmlFor="Contact-1-Name-4"
                                                       className="rl-field-label">Название</label>
                                                <input className="rl-form-input w-input"
                                                       onChange={handleChange}
                                                       name="name" data-name="Contact 1 Name 4"
                                                       placeholder="" type="text" id="Contact-1-Name-4" />
                                            </div>
                                            <div className="rl_contact1_form-field-wrapper">
                                                <label htmlFor="Contact-1-Email-4"
                                                       className="rl-field-label">Описание</label>
                                                <input className="rl-form-input w-input"
                                                       onChange={handleChange}
                                                       name="description" data-name="Contact 1 Email 4"
                                                       placeholder="" type="text" id="Contact-1-Email-4" />
                                            </div>
                                            <div id="w-node-_8d64cade-a67f-5204-5f63-d84654ad4331-c9e6aa5a"
                                                 className="rl_contact1_button-wrapper">
                                                <div className="rl_contact1_spacing-block-4"></div>
                                                <button
                                                    onClick={onCreate}
                                                       id="w-node-_8d64cade-a67f-5204-5f63-d84654ad4333-c9e6aa5a"
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

export default NewProject;
