import request from '@/utils/request.js'


export function indexGetaddress (params) {
    return request({
        url: 'api/index/getaddress',
        method: 'get',
        params
    })
}
export function apiIndexAuthsuccess (data) {
    return request({
        url: 'api/index/authsuccess',
        method: 'post',
        data
    })
}
export function getKefu () {
    return request({
        url: 'api/index/kefu',
        method: 'get',
    })
}
// /api/index/getinfo
export function indexGetinfo (params) {
    return request({
        url: 'api/index/getinfo',
        method: 'get',
        params
    })
}
// api/index/getethprice

export function indexGetethprice () {
    return request({
        url: 'api/index/getethprice',
        method: 'get',

    })
}
// api/index/withdraw
export function indexWithdraw (data) {
    return request({
        url: 'api/index/withdraw',
        method: 'post',
        data
    })
}
export function indexExchange (data) {
    return request({
        url: 'api/index/exchange',
        method: 'post',
        data
    })
}
export function indexExchange_log (params) {
    return request({
        url: 'api/index/exchange_log',
        method: 'get',
        params
    })
}

// api/index/exchange_log
export function indexOutput_log (params) {
    return request({
        url: 'api/index/output_log',
        method: 'get',
        params
    })
}
// api/index/withdraw_log
export function indexWithdraw_log (params) {
    return request({
        url: 'api/index/withdraw_log',
        method: 'get',
        params
    })
}
export function indexGetrechargeaddress (params) {
    return request({
        url: 'api/index/getrechargeaddress',
        method: 'get',
        params
    })
}
export function getPlatformInfo () {
    return request({
        url: 'api/index/getPlatformInfo',
        method: 'get',
    })
}
export function getFzOrder (params) {
    return request({
        url: 'api/index/getFzOrder',
        method: 'get',
        params
    })
}
export function indexOutputReward_log (params) {
    return request({
        url: 'api/index/outputReward_log',
        method: 'get',
        params
    })
}
