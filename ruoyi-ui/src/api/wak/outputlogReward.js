import request from '@/utils/request'

// 查询产出分润表列表
export function listOutputlogReward(query) {
  return request({
    url: '/wak/outputlogReward/list',
    method: 'get',
    params: query
  })
}

// 查询产出分润表详细
export function getOutputlogReward(id) {
  return request({
    url: '/wak/outputlogReward/' + id,
    method: 'get'
  })
}

// 新增产出分润表
export function addOutputlogReward(data) {
  return request({
    url: '/wak/outputlogReward',
    method: 'post',
    data: data
  })
}

// 修改产出分润表
export function updateOutputlogReward(data) {
  return request({
    url: '/wak/outputlogReward',
    method: 'put',
    data: data
  })
}

// 删除产出分润表
export function delOutputlogReward(id) {
  return request({
    url: '/wak/outputlogReward/' + id,
    method: 'delete'
  })
}
