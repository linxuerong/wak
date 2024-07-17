import request from '@/utils/request'

// 查询平台配置列表
export function listConfig(query) {
  return request({
    url: '/wak/config/list',
    method: 'get',
    params: query
  })
}

// 查询平台配置详细
export function getConfig(id) {
  return request({
    url: '/wak/config/' + id,
    method: 'get'
  })
}

// 新增平台配置
export function addConfig(data) {
  return request({
    url: '/wak/config',
    method: 'post',
    data: data
  })
}

// 修改平台配置
export function updateConfig(data) {
  return request({
    url: '/wak/config',
    method: 'put',
    data: data
  })
}

// 删除平台配置
export function delConfig(id) {
  return request({
    url: '/wak/config/' + id,
    method: 'delete'
  })
}
