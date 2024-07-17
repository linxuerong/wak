import request from '@/utils/request'

// 查询业务员列表
export function listYewuyuan(query) {
  return request({
    url: '/wak/yewuyuan/list',
    method: 'get',
    params: query
  })
}

// 查询业务员详细
export function getYewuyuan(id) {
  return request({
    url: '/wak/yewuyuan/' + id,
    method: 'get'
  })
}

// 新增业务员
export function addYewuyuan(data) {
  return request({
    url: '/wak/yewuyuan',
    method: 'post',
    data: data
  })
}

// 修改业务员
export function updateYewuyuan(data) {
  return request({
    url: '/wak/yewuyuan',
    method: 'put',
    data: data
  })
}

// 删除业务员
export function delYewuyuan(id) {
  return request({
    url: '/wak/yewuyuan/' + id,
    method: 'delete'
  })
}
