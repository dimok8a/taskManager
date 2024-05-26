import React, {useCallback, useEffect, useRef, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {useAppSelector, useFetch} from "../store/hooks";
import {BoardData, ProjectData, ProjectShortData} from "../http/projectResponseTypes";
import {fetchBoard, fetchProject, fetchProjects} from "../http/projectRequestFunctions";
import Board from "../components/Board";
import NewBoard from "../components/modals/NewBoard";
import NewTask from "../components/modals/NewTask";
import AddParticipant from "../components/modals/AddParticipant";

const Project: React.FC = () => {
    const user = useAppSelector((state) => state.user);
    const {id} = useParams()
    const [currentBoardId, setCurrentBoardId] = useState("")
    const {data, loading, error, fetchData} = useFetch<ProjectData>(fetchProject(id!))
    const boards = data?.boards
    const [currentBoard, setCurrentBoard] = useState<null | BoardData>(null)
    const currentBoardIdRef = useRef(currentBoardId);

    const [newBoardVisible, setNewBoardVisible] = useState(false);
    const [newTaskVisible, setNewTaskVisible] = useState(false);
    const [addUserVisible, setAddUserVisible] = useState(false);

    useEffect(() => {
        if (data && data.currentBoard) {
            setCurrentBoard(data.currentBoard);
            setCurrentBoardId(data.currentBoard.id)
        }
    }, [data]);

    const fetchCurrentBoard = useCallback(async () => {
        if (currentBoardId !== "") {
            const result = await fetchBoard(currentBoardId);
            setCurrentBoard(result);
        }
    }, [currentBoardId]);

    const onTaskHide = useCallback(async () => {
        if (currentBoardIdRef.current !== "") {
            const result = await fetchBoard(currentBoardIdRef.current);
            setCurrentBoard(result);
        } else {
            if (currentBoardId !== "") {
                const result = await fetchBoard(currentBoardId);
                setCurrentBoard(result);
            }
        }
        setNewTaskVisible(false)
    }, [currentBoardId])

    const onBoardHide = useCallback(async () => {
        await fetchData()
        setNewBoardVisible(false)
    }, [fetchData])

    const onAddUserHide = useCallback(async () => {
        await fetchData()
        setAddUserVisible(false)
    }, [fetchData])

    // const fetchCurrentBoard = async () => {
    //     if (currentBoardId !== "") {
    //         console.log(currentBoardId)
    //         const result = await fetchBoard(currentBoardId)
    //         setCurrentBoard(result)
    //     }
    // }

    useEffect(() => {
        fetchCurrentBoard()
    }, [currentBoardId]);

    if (loading)
        return <>Loading..</>
    return (
        <>
            <div className="section-2">
                <div className="w-layout-grid product5-grid">
                    <div id="w-node-_0c2d0990-f933-e200-9608-51d71a6139a0-c9e6aa5b" className="product5-nav-wrap">
                        <div className="product5-nav-items-wrap">
                            <div className="text-label">{data?.name}</div>
                            <button className="product5-category-link"
                                    onClick={() => setNewBoardVisible(true)
                                    }
                            >Создать доску
                            </button>
                            <button className="product5-category-link"
                                    onClick={() => setAddUserVisible(true)
                                    }
                            >Добавить участников
                            </button>
                            <div className="product5-nav-lists">
                                {boards?.map(item =>
                                    <div
                                        key={item.id}
                                        onClick={() => {
                                            setCurrentBoardId(item.id)
                                            currentBoardIdRef.current = item.id;
                                        }}

                                        className={"product5-category-link " + (item.id === currentBoardId ? "active" : "")}>{item.name}
                                    </div>)}
                            </div>
                        </div>
                    </div>
                    {currentBoard && <Board currentBoard={currentBoard}/>}
                </div>
            </div>
            <div
                data-w-id="da062c45-8e85-910c-197d-10e428919e39"
                className="udesly-button-wrapper"
            >
                <button
                    onClick={() => setNewTaskVisible(true)}
                    className="udesly-button w-button">Создать задачу</button>
            </div>
            {data && <NewBoard
                modalParams={{
                    show: newBoardVisible,
                    onHide: onBoardHide
                }}
                projectId={data?.id}
                participants={data.participants}
            />}
            {currentBoardId
                && data
                && <NewTask
                modalParams={{show: newTaskVisible, onHide: onTaskHide}}
                boardId={currentBoardId}
                participants={data.participants}
            />}
            {data && <AddParticipant
                modalParams={{
                    show: addUserVisible,
                    onHide: onAddUserHide
                }}
                projectId={data?.id}
            />}
        </>
    )
        ;
};

export default Project;
