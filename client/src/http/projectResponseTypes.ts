interface StandardData {
    id: string
}

export interface ProjectShortData extends StandardData {
    name: string,
    description: string
}

export interface ProjectDataSent {
    name: string
    description: string
}

export interface ProjectData extends StandardData {
    name: string,
    currentBoard: BoardData | null,
    boards: BoardShortData[],
    participants: ParticipantData[],
    owner: ParticipantData,
    description: string,
    createdAt: Date
}

export interface BoardData extends StandardData {
    name: string,
    sections: SectionData[],
    description: string
}

export interface BoardDataSent {
    projectId: string,
    name: string
    description: string
    inspectorId: string
}

export interface BoardShortData extends StandardData {
    name: string
}

export interface ParticipantData extends StandardData {
    nickname: string,
    firstname: string,
    lastname: string
}

export interface ParticipantDataSent {
    firstname: string,
    lastname: string,
}

export interface SectionData extends StandardData {
    type: string,
    tasks: TaskShortData[]
}

export interface TaskShortData extends StandardData {
    name: string
}


export interface SectionShortData extends StandardData {
    type: string
}


export interface TaskDataReceived extends StandardData {
    name: string,
    description: string,
    createdAt: Date,
    executor: ParticipantData | null,
    inspector: ParticipantData | null,
    section: SectionShortData
    participants: ParticipantData[]
}

export interface TaskDataToAddSent {
    boardId: string,
    name: string,
    description: string,
    executorId: string | null,
    inspectorId: string | null,
}

export interface TaskDataToChangeSent extends StandardData{
    name: string,
    description: string,
    executorId: string | null,
    inspectorId: string | null,
}

export interface AllUserTasksData {
    tasksToCheck: TasksWithProject[],
    tasksToExecute: TasksWithProject[]
}

export interface UserTasks {
    tasksWithProjects: TasksWithProject[]
}

export interface TasksWithProject {
    project: ProjectShortData,
    tasks: TaskShortData[]
}

export interface UserRegistrationData {
    nickname: string
    firstname: string
    lastname: string
    password1: string
    password2: string
}

export interface UserRegistrationDataSent {
    nickname: string
    firstname: string
    lastname: string
    hash: string
}

export interface UserLoginDataSent {
    nickname: string
    rnd: string
    hash: string
}

export interface AddUserToProjectDataSent {
    newUserNickname: string
}
