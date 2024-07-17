import request from '@/utils/request'

// 查询收益比例设置列表
export function listRate(query) {
  return request({
    url: '/wak/rate/list',
    method: 'get',
    params: query
  })
}

// 查询收益比例设置详细
export function getRate(id) {
  return request({
    url: '/wak/rate/' + id,
    method: 'get'
  })
}

// 新增收益比例设置
export function addRate(data) {
  return request({
    url: '/wak/rate',
    method: 'post',
    data: data
  })
}

// 修改收益比例设置
export function updateRate(data) {
  return request({
    url: '/wak/rate',
    method: 'put',
    data: data
  })
}

// 删除收益比例设置
export function delRate(id) {
  return request({
    url: '/wak/rate/' + id,
    method: 'delete'
  })
}
