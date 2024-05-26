import {Link, useLocation} from "react-router-dom";
import burgerMenu from "../assets/images/menu-burger.png"
import {useEffect, useState} from "react";
import {PROFILE_ROUTE, PROJECTS_ROUTE, TASKS_ROUTE} from "../consts";

export const Navbar = () => {

    const [isNavbarActive, setIsNavbarActive] = useState(false)

    const location = useLocation();

    const isActive = (path: string) => {
        return location.pathname === path
    }

    useEffect(() => {
        const overlayBlock = document.querySelector(".w-nav-overlay") as HTMLElement
        if (isNavbarActive)
        {
            overlayBlock.style.height = "800px"
            overlayBlock.style.display = "block"
            const menu = overlayBlock.querySelector(".w-nav-menu") as HTMLElement
            setTimeout(() => {
                menu.style.transform = "translateY(0px)"
            }, 0)
        } else
        {
            const menu = overlayBlock.querySelector(".w-nav-menu") as HTMLElement
            menu.style.transform = "translateY(-700px)"
            setTimeout(() => {
                overlayBlock.style.display = "none"
            }, 400)
        }
    }, [isNavbarActive]);
    return (
        <div data-collapse="medium" data-animation="default" data-duration="400" data-easing="ease" data-easing2="ease"
             role="banner" className="navigation w-nav">
            <div className="navigation-items">
                <Link to="/" className={"logo-link w-nav-brand"}>
                    <div className="logo-link">Task Manager<br/></div>
                </Link>
                <address className="navigation-wrap">
                    <nav role="navigation" className="navigation-items w-nav-menu">
                        <Link to={TASKS_ROUTE}
                              onClick={() => setIsNavbarActive(false)}
                              className={"navigation-item w-nav-link" + (isActive("/tasks") ? " w--current" : "")}>
                            Список задач
                        </Link>
                        <Link
                            to={PROJECTS_ROUTE} aria-current="page"
                            onClick={() => setIsNavbarActive(false)}
                            className={"navigation-item w-nav-link" + (isActive(PROJECTS_ROUTE) ? " w--current" : "")}>
                            Мои проекты
                        </Link>
                        <Link to={PROFILE_ROUTE}
                              onClick={() => setIsNavbarActive(false)}
                              className={"navigation-item w-nav-link" + (isActive(PROFILE_ROUTE) ? " w--current" : "")}>
                            Личный кабинет
                        </Link>
                    </nav>
                    <div className="menu-button w-nav-button"
                         onClick={() => setIsNavbarActive(prevState => !prevState)}
                    ><img width="30"
                                                                           src={burgerMenu}
                                                                           alt="" className="menu-icon"/>
                    </div>
                </address>
            </div>
            <div className="w-nav-overlay" data-wf-ignore="" id="w-nav-overlay-0"
                 style={{display: "none"}}
            >
                <nav role="navigation"
                     className="navigation-items w-nav-menu"
                     data-nav-menu-open=""
                     style={{transform: "translateY(-700px)", transition: "transform 400ms ease 0s"}}
                ><Link
                    onClick={() => setIsNavbarActive(false)}
                    className={"navigation-item w-nav-link w--nav-link-open" + (isActive(TASKS_ROUTE) ? " w--current" : "")} to={TASKS_ROUTE}>
                    Список задач
                </Link>
                    <Link
                    aria-current="page"
                    onClick={() => setIsNavbarActive(false)}
                    className={"navigation-item w-nav-link w--nav-link-open" + (isActive(PROJECTS_ROUTE) ? " w--current" : "")}
                    to={PROJECTS_ROUTE}>
                        Мои проекты
                    </Link>
                    <Link
                        onClick={() => setIsNavbarActive(false)}
                        className={"navigation-item w-nav-link w--nav-link-open" + (isActive(PROFILE_ROUTE) ? " w--current" : "")}
                                                       to={PROFILE_ROUTE}>
                        Личный кабинет
                    </Link>
                </nav>
            </div>
        </div>
    );
};
