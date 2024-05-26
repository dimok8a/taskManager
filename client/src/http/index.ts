import axios from "axios";

const $host = axios.create({
    baseURL: process.env.REACT_APP_API_URL as string
})

const $authHost = axios.create({
    baseURL: process.env.REACT_APP_API_URL as string
})

const authInterceptor = (config: any) => {
    const userToken = JSON.parse(localStorage.getItem('user')!).token
    config.headers.authorization = `Bearer ${userToken}`
    return config
}

$authHost.interceptors.request.use(authInterceptor)

export {
    $host,
    $authHost
}
