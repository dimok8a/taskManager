import React, {useEffect, useRef} from 'react';
import {ModalParams} from "./modalParams";
import ModalWrapper from "./modalWrapper";
import {useAppSelector, useFetch, useForm} from "../../store/hooks";
import {
    ProjectShortData,
    TaskDataReceived,
    TaskDataToAddSent,
    TaskDataToChangeSent
} from "../../http/projectResponseTypes";
import {
    changeTask, checkTask, completeTask,
    createTask,
    fetchProjects,
    fetchTask, rejectTask,
    takeTaskOnExecution
} from "../../http/projectRequestFunctions";
import {useParams} from "react-router-dom";
import {IN_PROGRESS, ON_CHECK, PREPARED} from "../../consts";

const Task = () => {
    const user = useAppSelector((state) => state.user);
    const {id} = useParams()
    const {data, loading, error, fetchData} = useFetch<TaskDataReceived>(fetchTask(id!));
    const { values, handleChange, resetForm, setValues } = useForm<TaskDataToChangeSent>({ id: id!, name: '', description: '', executorId: "0", inspectorId: "0" });
    const formRef = useRef<HTMLFormElement>(null)
    const statusRef = useRef<HTMLDivElement>(null)
    useEffect(() => {
        if (data) {
            console.log(data.executor)
            setValues({
                ...values,
                ...data,
                inspectorId: data.inspector?.id ?? null,
                executorId: data.executor?.id ?? null,
            })
        }
    }, [data]);



    const onSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        if (formRef.current?.checkValidity()) {
            console.log(values)
            changeTask(values).then(() => {
                statusRef.current!.textContent = "Изменения успешно сохранены!"
                // fetchData()
            })
        } else {
            formRef.current?.reportValidity();
        }
    }

    const onTakeToExecute = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        takeTaskOnExecution(id!).then(() => {
            statusRef.current!.textContent = "Задача успешно взята на выполнение"
            fetchData()
        })
    }

    const onApprove = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        checkTask(id!).then(() => {
            statusRef.current!.textContent = "Задача успешно проверена"
            fetchData()
        })
    }

    const onReject = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        rejectTask(id!).then(() => {
            statusRef.current!.textContent = "Задача успешно отклонена"
            fetchData()
        })
    }

    const onComplete = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        completeTask(id!).then(() => {
            statusRef.current!.textContent = "Задача успешно выполнена"
            fetchData()
        })
    }

    if (loading)
        return <h1>Loading..</h1>

    return (
            <section className="rl_section_contact1">
                <div className="rl-padding-global">
                    <div className="rl-container-small">
                        <div className="rl-padding-section-large">
                            <div className="rl_contact1_component">
                                <div className="rl_contact1_heading-wrapper">
                                    <div className="rl_contact1_spacing-block-1"></div>
                                    <h2 className="rl-heading-style-h2">Изменение данных задачи</h2>
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
                                                   onChange={handleChange}
                                                   name="name" data-name="Contact 1 Name" value={values.name}
                                                   type="text" id="Contact-1-Name-3"/>
                                        </div>
                                        <div className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="Contact-1-Email-3"
                                                   className="rl-field-label">Описание</label>
                                            <input className="rl-form-input w-input"
                                                   onChange={handleChange}
                                                   name="description"
                                                   value={values.description}
                                                   type="text" id="Contact-1-Email-3"/>
                                        </div>
                                        <div id="w-node-dcf49623-7454-d44f-44e4-1b61bed7430a-c9e6aa60"
                                             className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="field" className="rl-field-label">Исполнитель</label>
                                            <select
                                                onChange={handleChange}
                                                id="field" name="executorId" data-name="Field"
                                                className="select-field w-select"
                                                value={data?.executor?.id}

                                            >
                                                <option value="">Выберите исполнителя</option>
                                                {data?.participants.map(participant =>
                                                    <option key={participant.id} value={participant.id}>{participant.firstname + " " + participant.lastname}</option>
                                                )}
                                            </select>
                                        </div>
                                        <div id="w-node-f944fc00-dd0a-ef97-cfb5-2de7cb8c2564-c9e6aa60"
                                             className="rl_contact1_form-field-wrapper">
                                            <label htmlFor="field-2" className="rl-field-label">Проверяющий</label>
                                            <select id="field-2" name="inspectorId" data-name="Field 2"
                                                    className="select-field w-select"
                                                    value={data?.inspector?.id}
                                                    onChange={handleChange}>
                                                <option value="0">Выберите проверяющего</option>
                                                {data?.participants.map(participant =>
                                                    <option key={participant.id} value={participant.id}>{participant.firstname + " " + participant.lastname}</option>
                                                )}
                                            </select>
                                        </div>
                                        <div className="rl_contact1_form-field-wrapper">
                                            <div>Дата создания:</div>
                                            <div>{`${data?.createdAt.getDate()}.${data?.createdAt.getMonth()? (data?.createdAt.getMonth()+ 1) : "0"}.${data?.createdAt.getFullYear()}`}</div>
                                        </div>
                                        <div className="rl_contact1_form-field-wrapper">
                                            <div>Находится в секции:</div>
                                            <div>{data?.section.type}</div>
                                        </div>
                                        {
                                            data &&
                                            data.section.type === PREPARED
                                            && (data.executor?.id.toString() === user.id?.toString() || data.executor === null)
                                            && <div
                                            className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button
                                                onClick={onTakeToExecute}
                                                id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
                                                className="rl-button w-button">Приступить к выполнению
                                            </button>
                                        </div>}
                                        {
                                            data?.section.type === ON_CHECK
                                            && (data.inspector?.id.toString() === user.id?.toString())
                                            && <div
                                            className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button
                                                onClick={onApprove}
                                                id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
                                                className="rl-button w-button">Одобрить
                                            </button>
                                        </div>}
                                        {
                                            data?.section.type === ON_CHECK
                                            && (data.inspector?.id.toString() === user.id?.toString())
                                            && <div
                                                className="rl_contact1_button-wrapper">
                                                <div className="rl_contact1_spacing-block-4"></div>
                                                <button
                                                    onClick={onReject}
                                                    id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
                                                    className="rl-button w-button">Отклонить
                                                </button>
                                            </div>
                                        }
                                        {
                                            data?.section.type === IN_PROGRESS
                                            && data.executor?.id.toString() === user.id?.toString()
                                            && <div
                                                className="rl_contact1_button-wrapper">
                                                <div className="rl_contact1_spacing-block-4"></div>
                                                <button
                                                    onClick={onComplete}
                                                    id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
                                                    className="rl-button w-button">Завершить выполнение
                                                </button>
                                            </div>
                                        }
                                        <div
                                            className="rl_contact1_button-wrapper">
                                            <div className="rl_contact1_spacing-block-4"></div>
                                            <button
                                                onClick={onSave}
                                                id="w-node-_63158e05-966f-4bf9-e25d-e5e56984d92a-c9e6aa60"
                                                className="rl-button w-button">Сохранить изменения
                                            </button>
                                        </div>
                                        <div ref={statusRef}></div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
    );
};

export default Task;
