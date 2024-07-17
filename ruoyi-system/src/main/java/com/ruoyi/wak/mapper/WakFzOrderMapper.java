package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakFzOrder;

/**
 * 质押订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
public interface WakFzOrderMapper 
{
    /**
     * 查询质押订单
     * 
     * @param id 质押订单主键
     * @return 质押订单
     */
    public WakFzOrder selectWakFzOrderById(Long id);

    /**
     * 查询质押订单列表
     * 
     * @param wakFzOrder 质押订单
     * @return 质押订单集合
     */
    public List<WakFzOrder> selectWakFzOrderList(WakFzOrder wakFzOrder);

    /**
     * 新增质押订单
     * 
     * @param wakFzOrder 质押订单
     * @return 结果
     */
    public int insertWakFzOrder(WakFzOrder wakFzOrder);

    /**
     * 修改质押订单
     * 
     * @param wakFzOrder 质押订单
     * @return 结果
     */
    public int updateWakFzOrder(WakFzOrder wakFzOrder);

    /**
     * 删除质押订单
     * 
     * @param id 质押订单主键
     * @return 结果
     */
    public int deleteWakFzOrderById(Long id);

    /**
     * 批量删除质押订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakFzOrderByIds(Long[] ids);
}
