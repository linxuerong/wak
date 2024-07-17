import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
// import { getToken } from '@/utils/auth'

// create an axios instance
// console.log(process.env.VUE_APP_BASE_API);
const requestTime = 5 * 60 * 1000 //5分钟超时
const service = axios.create({

    baseURL:  location.protocol+'//' +location.host, // url = base url + request url
    // baseURL: process.env.VUE_APP_INTERFACE_URL, // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    // timeout: requestTime // request timeout
    timeout: requestTime // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        // if (store.getters.token) {
        //     config.headers['X-Token'] = getToken()
        //     config.headers['token'] = getToken()

        // }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)
const whiteApi = [
    'task/phone',
    'task/notTgPhone'
]
let loadingWhite = [
    'Wastask/ws_phone_all',
    'Wastask/ws_gender',
    'Wastask/ws_active'
]
// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
    */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */

    response => {
        // 大文件下载超时，轮询请求
        // loadingWhite.map((item) => {
        //     if (response.config.url.includes(item)) {
        //         store.state.app.loadingData = true
        //     } else {
        //         store.state.app.loadingData = false
        //     }
        // })
        const res = response.data
        let flag = false;
        whiteApi.map((item) => {
            if (response.config.url.includes(item))
                flag = true;
        })
        if (flag) {
            return res
        };

        // if(whiteApi.indexOf(config.url) != -1){
        // 	return config
        // }
        // if the custom code is not 20000, it is judged as an error.
        if (res.code == 1) {
            return res
        } else {
            Message({
                message: res.msg || 'Error',
                type: 'error',
                duration: 3 * 1000
            })

            // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
            if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                // to re-login
                MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
                    confirmButtonText: 'Re-Login',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    // store.dispatch('user/resetToken').then(() => {
                    //     location.reload()
                    // })
                })
            }
            return Promise.reject(new Error(res.msg || 'Error'))
        }
    },
    error => {
        console.log('请求超时') // for debug
        Message({
            message: error.message,
            type: 'error',
            duration: 3 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
