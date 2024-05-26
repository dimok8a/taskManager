import {
    HOME_ROUTE,
    LOGIN_ROUTE,
    NOT_FOUND_ROUTE, PROFILE_ROUTE,
    PROJECT_ROUTE,
    PROJECTS_ROUTE,
    REGISTRATION_ROUTE,
    TASK_ROUTE, TASKS_ROUTE
} from "./consts";
import Project from "./pages/Project";
import {FunctionComponent} from "react";
import Projects from "./pages/Projects";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import Task from "./components/modals/Task";
import {ModalParams} from "./components/modals/modalParams";
import Profile from "./pages/Profile";
import Tasks from "./pages/Tasks";

interface IRoute {
    path: string,
    component: FunctionComponent,
    withNavbar: boolean
}

export const authRoutes: IRoute[] = [
    {
        path: PROJECT_ROUTE + "/:id",
        component: Project,
        withNavbar: true
    },
    {
        path: PROJECTS_ROUTE,
        component: Projects,
        withNavbar: true
    },
    {
        path: TASK_ROUTE + "/:id",
        component: Task,
        withNavbar: true
    },
    {
        path: PROFILE_ROUTE,
        component: Profile,
        withNavbar: true
    },
    {
        path: TASKS_ROUTE,
        component: Tasks,
        withNavbar: true
    },
]

export const anonymousRoutes: IRoute[] = [
    {
        path: LOGIN_ROUTE,
        component: Login,
        withNavbar: false
    },
    {
        path: REGISTRATION_ROUTE,
        component: Registration,
        withNavbar: false
    },
]

export const publicRoutes: IRoute[] = [
    {
        path: HOME_ROUTE,
        component: Home,
        withNavbar: false
    },
    {
        path: NOT_FOUND_ROUTE,
        component: NotFound,
        withNavbar: true
    },
]
