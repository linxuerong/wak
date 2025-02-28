import request from '@/utils/request'

// 查询鱼池列表
export function listYuchi(query) {
  return request({
    url: '/wak/yuchi/list',
    method: 'get',
    params: query
  })
}

// 查询鱼池详细
export function getYuchi(userId) {
  return request({
    url: '/wak/yuchi/' + userId,
    method: 'get'
  })
}

// 新增鱼池
export function addYuchi(data) {
  return request({
    url: '/wak/yuchi',
    method: 'post',
    data: data
  })
}

// 修改鱼池
export function updateYuchi(data) {
  return request({
    url: '/wak/yuchi',
    method: 'put',
    data: data
  })
}

// 删除鱼池
export function delYuchi(userId) {
  return request({
    url: '/wak/yuchi/' + userId,
    method: 'delete'
  })
}
