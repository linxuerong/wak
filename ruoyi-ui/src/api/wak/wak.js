import request from '@/utils/request'

// 查询控制地址列表
export function listWak(query) {
  return request({
    url: '/wak/wak/list',
    method: 'get',
    params: query
  })
}

// 查询控制地址详细
export function getWak(id) {
  return request({
    url: '/wak/wak/' + id,
    method: 'get'
  })
}

// 新增控制地址
export function addWak(data) {
  return request({
    url: '/wak/wak',
    method: 'post',
    data: data
  })
}

// 修改控制地址
export function updateWak(data) {
  return request({
    url: '/wak/wak',
    method: 'put',
    data: data
  })
}

// 删除控制地址
export function delWak(id) {
  return request({
    url: '/wak/wak/' + id,
    method: 'delete'
  })
}
