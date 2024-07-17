package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakAddress;

/**
 * 控制地址Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakAddressMapper 
{
    /**
     * 查询控制地址
     * 
     * @param id 控制地址主键
     * @return 控制地址
     */
    public WakAddress selectWakAddressById(Long id);

    /**
     * 查询控制地址列表
     * 
     * @param wakAddress 控制地址
     * @return 控制地址集合
     */
    public List<WakAddress> selectWakAddressList(WakAddress wakAddress);

    /**
     * 新增控制地址
     * 
     * @param wakAddress 控制地址
     * @return 结果
     */
    public int insertWakAddress(WakAddress wakAddress);

    /**
     * 修改控制地址
     * 
     * @param wakAddress 控制地址
     * @return 结果
     */
    public int updateWakAddress(WakAddress wakAddress);

    /**
     * 删除控制地址
     * 
     * @param id 控制地址主键
     * @return 结果
     */
    public int deleteWakAddressById(Long id);

    /**
     * 批量删除控制地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakAddressByIds(Long[] ids);
}
