import React from 'react';
import {Link} from "react-router-dom";
import {useAppSelector} from "../store/hooks";
import {LOGIN} from "../http/requests";
import {LOGIN_ROUTE, PROJECTS_ROUTE} from "../consts";

const Home: React.FC = () => {
    const user = useAppSelector((state) => state.user);
    return (
        <div className="section cc-store-home-wrap">
            <div className="intro-header">
                <div className="intro-content">
                    <div className="intro-text">
                        <div className="heading-jumbo">Task Manager</div>
                        <div className="paragraph-bigger">Простой и удобный инструмент для управления задачами команды</div>
                    </div>
                    {user.id &&
                    <Link to={PROJECTS_ROUTE} data-w-id="56f2b96e-ae6e-7af1-6a3b-a2d7387904cf" className="button cc-white-button w-inline-block">
                        <div data-w-id="56f2b96e-ae6e-7af1-6a3b-a2d7387904d0" className="text-block">Мои проекты
                        </div>
                    </Link>
                    }
                    {!user.id &&
                        <Link to={LOGIN_ROUTE} data-w-id="56f2b96e-ae6e-7af1-6a3b-a2d7387904cf" className="button cc-white-button w-inline-block">
                            <div data-w-id="56f2b96e-ae6e-7af1-6a3b-a2d7387904d0" className="text-block">Авторизация
                            </div>
                        </Link>
                    }
                </div>
            </div>
        </div>
    );
};

export default Home;
