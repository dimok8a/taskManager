import {UserState} from "../store/userSlice";
import {
    AddUserToProjectDataSent,
    AllUserTasksData,
    BoardData, BoardDataSent,
    ParticipantData, ParticipantDataSent,
    ProjectData, ProjectDataSent,
    ProjectShortData,
    TaskDataReceived, TaskDataToAddSent, TaskDataToChangeSent, UserLoginDataSent, UserRegistrationDataSent
} from "./projectResponseTypes";
import {IN_PROGRESS, ON_CHECK, PREPARED} from "../consts";
import {$authHost, $host} from "./index";
import {
    ADD_USER_TO_PROJECT,
    CHANGE_TASK, CHANGE_USER_DATA, CHECK_TASK, COMPLETE_TASK,
    CREATE_BOARD, CREATE_PROJECT,
    CREATE_TASK, GET_ALL_USER_TASKS,
    GET_BOARD,
    GET_PROJECT,
    GET_PROJECTS,
    GET_TASK, GET_USER_DATA, LOGIN, LOGOUT, REGISTRATION, REJECT_TASK,
    TAKE_TASK_ON_EXECUTION
} from "./requests";
import newTask from "../components/modals/NewTask";


export const fetchProjects = () => async (): Promise<ProjectShortData[]> => {
    const {data} = await $authHost.get(GET_PROJECTS)
    return data.userProjects
    // console.log(userData.id)
    // return [{
    //     description: "Описание первого проекта", id: "1", name: "Первый проект"
    // }]
}

export const fetchProject = (projectId: string) => async (): Promise<ProjectData> => {
    const {data} = await $authHost.get(GET_PROJECT + projectId)
    console.log(data)
    return data
    // console.log(userData.id)
    // return {
    //     boards: [
    //         {id: "1", name: "Первая доска"},
    //         {id: "2", name: "Вторая доска"},
    //     ],
    //     createdAt: new Date(),
    //     currentBoard: {
    //         id: "1",
    //         name: "Первая доска",
    //         description: "Описание первой доски",
    //         sections: [
    //             {
    //                 id: "1",
    //                 type: "Готовы к выполнению",
    //                 tasks: [{
    //                     id: "1", name: "Задача 1"
    //                 },
    //                     {
    //                         id: "2", name: "Задача 2"
    //                     }]
    //             },
    //             {
    //                 id: "2",
    //                 type: "В процессе",
    //                 tasks: [{
    //                     id: "2", name: "Задача 2"
    //                 }]
    //             },
    //             {
    //                 id: "3",
    //                 type: "На проверке",
    //                 tasks: [{
    //                     id: "3", name: "Задача 3"
    //                 }]
    //             },
    //             {
    //                 id: "4",
    //                 type: "Готово",
    //                 tasks: [{
    //                     id: "4", name: "Задача 4"
    //                 }]
    //             }
    //         ]
    //     },
    //     description: "Это первый проект",
    //     id: "1",
    //     name: "Первый проект",
    //     owner: {
    //         id: "1",
    //         nickname: "Dmitry",
    //         firstname: "Dmitry",
    //         lastname: "Okishev"
    //     },
    //     participants: [{
    //         id: "1",
    //         nickname: "Dmitry",
    //         firstname: "Dmitry",
    //         lastname: "Okishev"
    //     }]
    //
    // }
}

export const createProject = async (newProject: ProjectDataSent) => {
    const {data} = await $authHost.post(CREATE_PROJECT, newProject)
    console.log(data)
    return data
    // console.log(userData.id)
    // return {
    //     boards: [
    //         {id: "1", name: "Первая доска"},
    //         {id: "2", name: "Вторая доска"},
    //     ],
    //     createdAt: new Date(),
    //     currentBoard: {
    //         id: "1",
    //         name: "Первая доска",
    //         description: "Описание первой доски",
    //         sections: [
    //             {
    //                 id: "1",
    //                 type: "Готовы к выполнению",
    //                 tasks: [{
    //                     id: "1", name: "Задача 1"
    //                 },
    //                     {
    //                         id: "2", name: "Задача 2"
    //                     }]
    //             },
    //             {
    //                 id: "2",
    //                 type: "В процессе",
    //                 tasks: [{
    //                     id: "2", name: "Задача 2"
    //                 }]
    //             },
    //             {
    //                 id: "3",
    //                 type: "На проверке",
    //                 tasks: [{
    //                     id: "3", name: "Задача 3"
    //                 }]
    //             },
    //             {
    //                 id: "4",
    //                 type: "Готово",
    //                 tasks: [{
    //                     id: "4", name: "Задача 4"
    //                 }]
    //             }
    //         ]
    //     },
    //     description: "Это первый проект",
    //     id: "1",
    //     name: "Первый проект",
    //     owner: {
    //         id: "1",
    //         nickname: "Dmitry",
    //         firstname: "Dmitry",
    //         lastname: "Okishev"
    //     },
    //     participants: [{
    //         id: "1",
    //         nickname: "Dmitry",
    //         firstname: "Dmitry",
    //         lastname: "Okishev"
    //     }]
    //
    // }
}

export const createTask = async (newTask: TaskDataToAddSent) => {
    const {data} = await $authHost.post(CREATE_TASK, newTask)
    console.log(data)
    // return data
}

export const changeTask = async (changedTask: TaskDataToChangeSent) => {
    const {data} = await $authHost.put(CHANGE_TASK + changedTask.id, changedTask)
    console.log(data)
    // return data
}

export const takeTaskOnExecution = async (taskId: string) => {
    const {data} = await $authHost.put(TAKE_TASK_ON_EXECUTION + taskId)
    console.log(data)
    // return data
}

export const checkTask = async (taskId: string) => {
    const {data} = await $authHost.put(CHECK_TASK + taskId)
    console.log(data)
    // return data
}

export const rejectTask = async (taskId: string) => {
    const {data} = await $authHost.put(REJECT_TASK + taskId)
    console.log(data)
    // return data
}

export const completeTask = async (taskId: string) => {
    const {data} = await $authHost.put(COMPLETE_TASK + taskId)
    console.log(data)
    // return data
}

export const createBoard = async (newBoard: BoardDataSent) => {
    const {data} = await $authHost.post(CREATE_BOARD, newBoard)
    console.log(data)
}

export const fetchBoard = async (boardId: string): Promise<BoardData> => {
    const {data} = await $authHost.get(GET_BOARD + boardId)
    return data
    // return {
    //         id: "2",
    //         name: "Вторая доска",
    //         description: "Описание второй доски",
    //         sections: [
    //             {
    //                 id: "1",
    //                 type: "Готовы к выполнению",
    //                 tasks: [{
    //                     id: "1", name: "Задача 123"
    //                 },
    //                     {
    //                         id: "2", name: "Задача 123"
    //                     }]
    //             },
    //             {
    //                 id: "2",
    //                 type: "В процессе",
    //                 tasks: [{
    //                     id: "2", name: "Задача 432"
    //                 }]
    //             },
    //             {
    //                 id: "3",
    //                 type: "На проверке",
    //                 tasks: [{
    //                     id: "3", name: "Задача 123"
    //                 }]
    //             },
    //             {
    //                 id: "4",
    //                 type: "Готово",
    //                 tasks: [{
    //                     id: "4", name: "Задача 234"
    //                 }]
    //             }
    //         ]
    //     }
}

export const fetchTask = (taskId: string) => async (): Promise<TaskDataReceived> => {
    const {data} = await $authHost.get(GET_TASK + taskId)
    console.log(data)
    return {
        ...data,
        createdAt: new Date(data.createdAt)
    }
    // return data
    // return {
    //     id: "1",
    //     name: "Первая задача",
    //     description: "Описание первой задачи",
    //     createdAt: new Date(),
    //     executor: {id: "1", nickname: "Steve", firstname: "Steve", lastname: "Steve"},
    //     inspector: {id: "1", nickname: "Steve", firstname: "Steve", lastname: "Steve"},
    //     section: {
    //         id: "1",
    //         type: ON_CHECK
    //     }
    // }

}

export const fetchUserData = () => async (): Promise<ParticipantData> => {
    const {data} = await $authHost.get(GET_USER_DATA)
    console.log(data)
    return data
    // return {
    //     id: "2",
    //     nickname: "Steve",
    //     firstname: "Firstname",
    //     lastname: "Lastname",
    // }

}

export const changeUserData = async (userData : ParticipantDataSent) => {
    const {data} = await $authHost.put(CHANGE_USER_DATA, userData)
    console.log(data)
    return data
    // return {
    //     id: "2",
    //     nickname: "Steve",
    //     firstname: "Firstname",
    //     lastname: "Lastname",
    // }

}

export const logout = async () => {
    const {data} = await $authHost.post(LOGOUT)
    console.log(data)
    return data
}

export const register = async (userData: UserRegistrationDataSent) => {
    const {data} = await $host.post(REGISTRATION, userData)
    console.log(data)
    return data
}

export const login = async (userData: UserLoginDataSent) => {
    const {data} = await $host.post(LOGIN, userData)
    console.log(data)
    return data
}

export const fetchAllUserTasksData = () => async (): Promise<AllUserTasksData> => {
    const {data} = await $authHost.get(GET_ALL_USER_TASKS)
    return {
        tasksToCheck: data.tasksToCheck.tasksWithProjects,
        tasksToExecute: data.tasksToExecute.tasksWithProjects
    }

}

export const addUserToProject = async (userToProjectDataSent: AddUserToProjectDataSent, projectId: string) => {
    const {data} = await $authHost.put(ADD_USER_TO_PROJECT + projectId, userToProjectDataSent)
    console.log(data)
    return data

}
