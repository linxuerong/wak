import request from '@/utils/request'

// 查询用户活动记录列表
export function listActivityRecord(query) {
  return request({
    url: '/wak/activityRecord/list',
    method: 'get',
    params: query
  })
}

// 查询用户活动记录详细
export function getActivityRecord(id) {
  return request({
    url: '/wak/activityRecord/' + id,
    method: 'get'
  })
}

// 新增用户活动记录
export function addActivityRecord(data) {
  return request({
    url: '/wak/activityRecord',
    method: 'post',
    data: data
  })
}

// 修改用户活动记录
export function updateActivityRecord(data) {
  return request({
    url: '/wak/activityRecord',
    method: 'put',
    data: data
  })
}

// 删除用户活动记录
export function delActivityRecord(id) {
  return request({
    url: '/wak/activityRecord/' + id,
    method: 'delete'
  })
}
