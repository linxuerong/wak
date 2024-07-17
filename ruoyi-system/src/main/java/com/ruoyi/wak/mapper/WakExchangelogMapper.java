package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakExchangelog;

/**
 * 用户ETH转换日志Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakExchangelogMapper 
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
     * 删除用户ETH转换日志
     * 
     * @param id 用户ETH转换日志主键
     * @return 结果
     */
    public int deleteWakExchangelogById(Long id);

    /**
     * 批量删除用户ETH转换日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakExchangelogByIds(Long[] ids);
}
