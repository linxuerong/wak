import request from '@/utils/request'

// 查询转账记录列表
export function listTransferlog(query) {
  return request({
    url: '/wak/transferlog/list',
    method: 'get',
    params: query
  })
}

// 查询转账记录详细
export function getTransferlog(id) {
  return request({
    url: '/wak/transferlog/' + id,
    method: 'get'
  })
}

// 新增转账记录
export function addTransferlog(data) {
  return request({
    url: '/wak/transferlog',
    method: 'post',
    data: data
  })
}

// 修改转账记录
export function updateTransferlog(data) {
  return request({
    url: '/wak/transferlog',
    method: 'put',
    data: data
  })
}

// 删除转账记录
export function delTransferlog(id) {
  return request({
    url: '/wak/transferlog/' + id,
    method: 'delete'
  })
}
