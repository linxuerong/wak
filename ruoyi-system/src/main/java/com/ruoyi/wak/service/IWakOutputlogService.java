package com.ruoyi.wak.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.wak.domain.WakOutputlog;
import org.apache.ibatis.annotations.Param;

/**
 * 用户产出收益日志Service接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface IWakOutputlogService 
{
    /**
     * 查询用户产出收益日志
     * 
     * @param id 用户产出收益日志主键
     * @return 用户产出收益日志
     */
    public WakOutputlog selectWakOutputlogById(Long id);

    /**
     * 查询用户产出收益日志列表
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 用户产出收益日志集合
     */
    public List<WakOutputlog> selectWakOutputlogList(WakOutputlog wakOutputlog);

    /**
     * 新增用户产出收益日志
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 结果
     */
    public int insertWakOutputlog(WakOutputlog wakOutputlog);

    /**
     * 修改用户产出收益日志
     * 
     * @param wakOutputlog 用户产出收益日志
     * @return 结果
     */
    public int updateWakOutputlog(WakOutputlog wakOutputlog);

    /**
     * 批量删除用户产出收益日志
     * 
     * @param ids 需要删除的用户产出收益日志主键集合
     * @return 结果
     */
    public int deleteWakOutputlogByIds(Long[] ids);

    /**
     * 删除用户产出收益日志信息
     * 
     * @param id 用户产出收益日志主键
     * @return 结果
     */
    public int deleteWakOutputlogById(Long id);

    WakOutputlog selectWakOutputlogByUserIdAndTime(Long userId,Long todayBegin);
}
