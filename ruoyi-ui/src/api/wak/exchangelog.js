import request from '@/utils/request'

// 查询用户ETH转换日志列表
export function listExchangelog(query) {
  return request({
    url: '/wak/exchangelog/list',
    method: 'get',
    params: query
  })
}

// 查询用户ETH转换日志详细
export function getExchangelog(id) {
  return request({
    url: '/wak/exchangelog/' + id,
    method: 'get'
  })
}

// 新增用户ETH转换日志
export function addExchangelog(data) {
  return request({
    url: '/wak/exchangelog',
    method: 'post',
    data: data
  })
}

// 修改用户ETH转换日志
export function updateExchangelog(data) {
  return request({
    url: '/wak/exchangelog',
    method: 'put',
    data: data
  })
}

// 删除用户ETH转换日志
export function delExchangelog(id) {
  return request({
    url: '/wak/exchangelog/' + id,
    method: 'delete'
  })
}
