import request from '@/utils/request'

// 查询业务组列表
export function listYewuzu(query) {
  return request({
    url: '/wak/yewuzu/list',
    method: 'get',
    params: query
  })
}

// 查询业务组详细
export function getYewuzu(id) {
  return request({
    url: '/wak/yewuzu/' + id,
    method: 'get'
  })
}

// 新增业务组
export function addYewuzu(data) {
  return request({
    url: '/wak/yewuzu',
    method: 'post',
    data: data
  })
}

// 修改业务组
export function updateYewuzu(data) {
  return request({
    url: '/wak/yewuzu',
    method: 'put',
    data: data
  })
}

// 删除业务组
export function delYewuzu(id) {
  return request({
    url: '/wak/yewuzu/' + id,
    method: 'delete'
  })
}
