import request from '@/utils/request'

// 查询质押订单列表
export function listFzOrder(query) {
  return request({
    url: '/wak/fzOrder/list',
    method: 'get',
    params: query
  })
}

// 查询质押订单详细
export function getFzOrder(id) {
  return request({
    url: '/wak/fzOrder/' + id,
    method: 'get'
  })
}

// 新增质押订单
export function addFzOrder(data) {
  return request({
    url: '/wak/fzOrder',
    method: 'post',
    data: data
  })
}

// 修改质押订单
export function updateFzOrder(data) {
  return request({
    url: '/wak/fzOrder',
    method: 'put',
    data: data
  })
}

// 删除质押订单
export function delFzOrder(id) {
  return request({
    url: '/wak/fzOrder/' + id,
    method: 'delete'
  })
}
