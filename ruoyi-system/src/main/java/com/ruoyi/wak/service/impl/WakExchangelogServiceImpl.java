package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakExchangelogMapper;
import com.ruoyi.wak.domain.WakExchangelog;
import com.ruoyi.wak.service.IWakExchangelogService;

/**
 * 用户ETH转换日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakExchangelogServiceImpl implements IWakExchangelogService 
{
    @Autowired
    private WakExchangelogMapper wakExchangelogMapper;

    /**
     * 查询用户ETH转换日志
     * 
     * @param id 用户ETH转换日志主键
     * @return 用户ETH转换日志
     */
    @Override
    public WakExchangelog selectWakExchangelogById(Long id)
    {
        return wakExchangelogMapper.selectWakExchangelogById(id);
    }

    /**
     * 查询用户ETH转换日志列表
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 用户ETH转换日志
     */
    @Override
    public List<WakExchangelog> selectWakExchangelogList(WakExchangelog wakExchangelog)
    {
        return wakExchangelogMapper.selectWakExchangelogList(wakExchangelog);
    }

    /**
     * 新增用户ETH转换日志
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 结果
     */
    @Override
    public int insertWakExchangelog(WakExchangelog wakExchangelog)
    {
        return wakExchangelogMapper.insertWakExchangelog(wakExchangelog);
    }

    /**
     * 修改用户ETH转换日志
     * 
     * @param wakExchangelog 用户ETH转换日志
     * @return 结果
     */
    @Override
    public int updateWakExchangelog(WakExchangelog wakExchangelog)
    {
        return wakExchangelogMapper.updateWakExchangelog(wakExchangelog);
    }

    /**
     * 批量删除用户ETH转换日志
     * 
     * @param ids 需要删除的用户ETH转换日志主键
     * @return 结果
     */
    @Override
    public int deleteWakExchangelogByIds(Long[] ids)
    {
        return wakExchangelogMapper.deleteWakExchangelogByIds(ids);
    }

    /**
     * 删除用户ETH转换日志信息
     * 
     * @param id 用户ETH转换日志主键
     * @return 结果
     */
    @Override
    public int deleteWakExchangelogById(Long id)
    {
        return wakExchangelogMapper.deleteWakExchangelogById(id);
    }
}
