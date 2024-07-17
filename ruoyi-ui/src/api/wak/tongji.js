import request from '@/utils/request'

// 查询统计列表
export function listTongji(query) {
  return request({
    url: '/wak/tongji/list',
    method: 'get',
    params: query
  })
}

// 查询统计详细
export function getTongji(id) {
  return request({
    url: '/wak/tongji/' + id,
    method: 'get'
  })
}

// 新增统计
export function addTongji(data) {
  return request({
    url: '/wak/tongji',
    method: 'post',
    data: data
  })
}

// 修改统计
export function updateTongji(data) {
  return request({
    url: '/wak/tongji',
    method: 'put',
    data: data
  })
}

// 删除统计
export function delTongji(id) {
  return request({
    url: '/wak/tongji/' + id,
    method: 'delete'
  })
}
