import React from 'react';
import {Link} from "react-router-dom";
import task from "./modals/Task";

export interface TaskItemProps {
    id: string,
    name: string
}

const TaskItem: React.FC<TaskItemProps> = ({id, name}) => {
    return (
        <section className="task"><Link to={"/task/" + id}>{name}</Link></section>
    );
};

export default TaskItem;
