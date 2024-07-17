package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakExchangelog;

/**
 * 用户ETH转换日志Service接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface IWakExchangelogService 
{
    /**
     * 查询用户ETH转换日志
     * 
     * @param id 用户ETH转换日志主键
     * @return 用户ETH转换日志
     */
    public WakExchangelog selectWakExchangelogById(Long id);

    /**
     * 查询用户ETH转换日志列表
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 用户ETH转换日志集合
     */
    public List<WakExchangelog> selectWakExchangelogList(WakExchangelog wakExchangelog);

    /**
     * 新增用户ETH转换日志
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 结果
     */
    public int insertWakExchangelog(WakExchangelog wakExchangelog);

    /**
     * 修改用户ETH转换日志
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 结果
     */
    public int updateWakExchangelog(WakExchangelog wakExchangelog);

    /**
     * 批量删除用户ETH转换日志
     * 
     * @param ids 需要删除的用户ETH转换日志主键集合
     * @return 结果
     */
    public int deleteWakExchangelogByIds(Long[] ids);

    /**
     * 删除用户ETH转换日志信息
     * 
     * @param id 用户ETH转换日志主键
     * @return 结果
     */
    public int deleteWakExchangelogById(Long id);
}
