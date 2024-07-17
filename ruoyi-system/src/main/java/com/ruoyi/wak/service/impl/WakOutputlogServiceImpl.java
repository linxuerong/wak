package com.ruoyi.wak.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakOutputlogMapper;
import com.ruoyi.wak.domain.WakOutputlog;
import com.ruoyi.wak.service.IWakOutputlogService;

/**
 * 用户产出收益日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakOutputlogServiceImpl implements IWakOutputlogService 
{
    @Autowired
    private WakOutputlogMapper wakOutputlogMapper;

    /**
     * 查询用户产出收益日志
     * 
     * @param id 用户产出收益日志主键
     * @return 用户产出收益日志
     */
    @Override
    public WakOutputlog selectWakOutputlogById(Long id)
    {
        return wakOutputlogMapper.selectWakOutputlogById(id);
    }

    /**
     * 查询用户产出收益日志列表
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 用户产出收益日志
     */
    @Override
    public List<WakOutputlog> selectWakOutputlogList(WakOutputlog wakOutputlog)
    {
        return wakOutputlogMapper.selectWakOutputlogList(wakOutputlog);
    }

    /**
     * 新增用户产出收益日志
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 结果
     */
    @Override
    public int insertWakOutputlog(WakOutputlog wakOutputlog)
    {
        return wakOutputlogMapper.insertWakOutputlog(wakOutputlog);
    }

    /**
     * 修改用户产出收益日志
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 结果
     */
    @Override
    public int updateWakOutputlog(WakOutputlog wakOutputlog)
    {
        return wakOutputlogMapper.updateWakOutputlog(wakOutputlog);
    }

    /**
     * 批量删除用户产出收益日志
     * 
     * @param ids 需要删除的用户产出收益日志主键
     * @return 结果
     */
    @Override
    public int deleteWakOutputlogByIds(Long[] ids)
    {
        return wakOutputlogMapper.deleteWakOutputlogByIds(ids);
    }

    /**
     * 删除用户产出收益日志信息
     * 
     * @param id 用户产出收益日志主键
     * @return 结果
     */
    @Override
    public int deleteWakOutputlogById(Long id)
    {
        return wakOutputlogMapper.deleteWakOutputlogById(id);
    }

    @Override
    public WakOutputlog selectWakOutputlogByUserIdAndTime(Long userId, Long todayBegin) {
        return wakOutputlogMapper.selectWakOutputlogByUserIdAndTime(userId,todayBegin);
    }
}
