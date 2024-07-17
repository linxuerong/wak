import request from '@/utils/request'

// 查询用户提现日志列表
export function listWithdrawlog(query) {
  return request({
    url: '/wak/withdrawlog/list',
    method: 'get',
    params: query
  })
}

// 查询用户提现日志详细
export function getWithdrawlog(id) {
  return request({
    url: '/wak/withdrawlog/' + id,
    method: 'get'
  })
}

// 新增用户提现日志
export function addWithdrawlog(data) {
  return request({
    url: '/wak/withdrawlog',
    method: 'post',
    data: data
  })
}

// 修改用户提现日志
export function updateWithdrawlog(data) {
  return request({
    url: '/wak/withdrawlog',
    method: 'put',
    data: data
  })
}
// 修改用户提现日志
export function passWithdrawlog(data) {
  return request({
    url: '/wak/withdrawlog/pass',
    method: 'post',
    data: data
  })
}

// 删除用户提现日志
export function delWithdrawlog(id) {
  return request({
    url: '/wak/withdrawlog/' + id,
    method: 'delete'
  })
}
