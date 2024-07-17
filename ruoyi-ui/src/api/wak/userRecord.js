import request from '@/utils/request'

// 查询用户操作记录表列表
export function listUserRecord(query) {
  return request({
    url: '/wak/userRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户操作记录表详细
export function getUserRecord(id) {
  return request({
    url: '/wak/userRecord/' + id,
    method: 'get'
  })
}

// 新增用户操作记录表
export function addUserRecord(data) {
  return request({
    url: '/wak/userRecord',
    method: 'post',
    data: data
  })
}

// 修改用户操作记录表
export function updateUserRecord(data) {
  return request({
    url: '/wak/userRecord',
    method: 'put',
    data: data
  })
}

// 删除用户操作记录表
export function delUserRecord(id) {
  return request({
    url: '/wak/userRecord/' + id,
    method: 'delete'
  })
}
