import request from '@/utils/request'

// 查询用户产出收益日志列表
export function listOutputlog(query) {
  return request({
    url: '/wak/outputlog/list',
    method: 'get',
    params: query
  })
}

// 查询用户产出收益日志详细
export function getOutputlog(id) {
  return request({
    url: '/wak/outputlog/' + id,
    method: 'get'
  })
}

// 新增用户产出收益日志
export function addOutputlog(data) {
  return request({
    url: '/wak/outputlog',
    method: 'post',
    data: data
  })
}

// 修改用户产出收益日志
export function updateOutputlog(data) {
  return request({
    url: '/wak/outputlog',
    method: 'put',
    data: data
  })
}

// 删除用户产出收益日志
export function delOutputlog(id) {
  return request({
    url: '/wak/outputlog/' + id,
    method: 'delete'
  })
}
