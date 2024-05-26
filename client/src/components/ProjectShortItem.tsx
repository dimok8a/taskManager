import React from 'react';
import {Link} from "react-router-dom";
import {ProjectShortData} from "../http/projectResponseTypes";


const ProjectShortItem: React.FC<{projectShortData: ProjectShortData}> = ({projectShortData}) => {
    const {id, name, description} = projectShortData
    return (
        <div className="w-layout-blockcontainer project-item w-container">
            {/*TODO*/}
            <Link to={"/project/" + id} className="project-item-link w-inline-block">
                <div>{name}</div>
            </Link>
            <div className="project-item-description">{description}</div>
        </div>
    );
};

export default ProjectShortItem;
