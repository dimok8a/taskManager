import React from 'react';
import {BoardData, ProjectShortData} from "../http/projectResponseTypes";
import {Link} from "react-router-dom";


const Board: React.FC<{currentBoard: BoardData}> = ({currentBoard}) => {
    const {sections} = currentBoard
    return (
        <div id="w-node-_0c2d0990-f933-e200-9608-51d71a6139bb-c9e6aa5b"
             className="w-layout-grid product5-feed-grid">
            {sections.map(item =>
                <div className="section-block grid-board-area">
                    <div className="product3-content-wrap">
                        <div className="size4-text">{item.type}</div>
                    </div>
                    <div className="w-layout-blockcontainer task-container w-container">
                        {item.tasks.map(task =>
                            <section className="task"><Link to={"/task/" + task.id}>{task.name}</Link></section>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
};

export default Board;
