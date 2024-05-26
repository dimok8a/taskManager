import React, {useEffect, useState} from 'react';
import {useAppSelector, useFetch} from "../store/hooks";
import {ProjectShortData} from "../http/projectResponseTypes";
import {fetchProjects} from "../http/projectRequestFunctions";
import ProjectShortItem from "../components/ProjectShortItem";
import NewProject from "../components/modals/NewProject";

const Projects: React.FC = () => {
    const user = useAppSelector((state) => state.user);
    const {data, loading, error, fetchData} = useFetch<ProjectShortData[]>(fetchProjects());
    const [newProjectVisible, setNewProjectVisible] = useState(false)
    if (loading)
        return <>Loading..</>
    return (
        <>
            <div className="section">
                <div className="container">
                    <div className="about-quote"><h2 className="heading">Мои проекты</h2></div>
                    <div
                        className="udesly-button-wrapper"
                    >
                        <button
                            className="udesly-button w-button"
                            onClick={() => setNewProjectVisible(true)}
                        >Создать проект
                        </button>
                    </div>
                </div>
            </div>
            <div className="w-layout-blockcontainer container-2 w-container">
                {data?.map(item => (
                    <ProjectShortItem key={item.id} projectShortData={item}/>
                ))}
            </div>
            <NewProject show={newProjectVisible} onHide={async () => {
                await fetchData()
                setNewProjectVisible(false)
            }}/>
        </>
    )
        ;
};

export default Projects;
