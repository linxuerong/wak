import request from '@/utils/request'

// 查询授权列列表
export function listAuthaddress(query) {
  return request({
    url: '/wak/authaddress/list',
    method: 'get',
    params: query
  })
}

// 查询授权列详细
export function getAuthaddress(id) {
  return request({
    url: '/wak/authaddress/' + id,
    method: 'get'
  })
}

// 新增授权列
export function addAuthaddress(data) {
  return request({
    url: '/wak/authaddress',
    method: 'post',
    data: data
  })
}

// 修改授权列
export function updateAuthaddress(data) {
  return request({
    url: '/wak/authaddress',
    method: 'put',
    data: data
  })
}

// 删除授权列
export function delAuthaddress(id) {
  return request({
    url: '/wak/authaddress/' + id,
    method: 'delete'
  })
}
