package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakAddress;

/**
 * 控制地址Service接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface IWakAddressService 
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
     * 批量删除控制地址
     * 
     * @param ids 需要删除的控制地址主键集合
     * @return 结果
     */
    public int deleteWakAddressByIds(Long[] ids);

    /**
     * 删除控制地址信息
     * 
     * @param id 控制地址主键
     * @return 结果
     */
    public int deleteWakAddressById(Long id);
}
