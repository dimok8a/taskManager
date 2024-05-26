import React, {useState} from 'react';
import {useAppSelector, useFetch} from "../store/hooks";
import {AllUserTasksData, ProjectShortData} from "../http/projectResponseTypes";
import {fetchAllUserTasksData, fetchProjects} from "../http/projectRequestFunctions";
import {Link} from "react-router-dom";
import TaskItem from "../components/TaskItem";
import {PROJECT_ROUTE} from "../consts";

const Tasks = () => {
    const user = useAppSelector((state) => state.user);
    const {data, loading, error} = useFetch<AllUserTasksData>(fetchAllUserTasksData());
    if (loading)
        return <>Loading..</>
    return (
        <>
            <div className="section">
                <div className="container">
                    <div className="about-quote"><h2 className="heading">Я - исполнитель</h2></div>
                </div>
            </div>
            <div className="w-layout-blockcontainer container-2 w-container">
                {data?.tasksToExecute.map(item =>
                    <>
                        <Link to={PROJECT_ROUTE + "/" + item.project.id} className="project-item-link w-inline-block">
                            <div>{item.project.name}</div>
                        </Link>
                        {item.tasks.map(task => <TaskItem key={task.id} id={task.id} name={task.name} />)}
                    </>
                )}
            </div>
            <div className="section">
                <div className="container">
                    <div className="about-quote"><h2 className="heading">Я - проверяющий</h2></div>
                </div>
            </div>
            <div className="w-layout-blockcontainer container-2 w-container">
                {data?.tasksToCheck.map(item =>
                    <>
                        <Link to={PROJECT_ROUTE + "/" + item.project.id} className="project-item-link w-inline-block">
                            <div>{item.project.name}</div>
                        </Link>
                        {item.tasks.map(task => <TaskItem key={task.id} id={task.id} name={task.name} />)}
                    </>
                )}
            </div>
        </>
    );
};

export default Tasks;
