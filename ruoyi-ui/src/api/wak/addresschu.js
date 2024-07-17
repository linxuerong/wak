import request from '@/utils/request'

// 查询出款地址管理列表
export function listAddresschu(query) {
  return request({
    url: '/wak/addresschu/list',
    method: 'get',
    params: query
  })
}

// 查询出款地址管理详细
export function getAddresschu(id) {
  return request({
    url: '/wak/addresschu/' + id,
    method: 'get'
  })
}

// 新增出款地址管理
export function addAddresschu(data) {
  return request({
    url: '/wak/addresschu',
    method: 'post',
    data: data
  })
}

// 修改出款地址管理
export function updateAddresschu(data) {
  return request({
    url: '/wak/addresschu',
    method: 'put',
    data: data
  })
}

// 删除出款地址管理
export function delAddresschu(id) {
  return request({
    url: '/wak/addresschu/' + id,
    method: 'delete'
  })
}
