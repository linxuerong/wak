package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakActivity;

/**
 * 活动表Mapper接口
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
public interface WakActivityMapper 
{
    /**
     * 查询活动表
     * 
     * @param id 活动表主键
     * @return 活动表
     */
    public WakActivity selectWakActivityById(Long id);

    /**
     * 查询活动表列表
     * 
     * @param wakActivity 活动表
     * @return 活动表集合
     */
    public List<WakActivity> selectWakActivityList(WakActivity wakActivity);

    /**
     * 新增活动表
     * 
     * @param wakActivity 活动表
     * @return 结果
     */
    public int insertWakActivity(WakActivity wakActivity);

    /**
     * 修改活动表
     * 
     * @param wakActivity 活动表
     * @return 结果
     */
    public int updateWakActivity(WakActivity wakActivity);

    /**
     * 删除活动表
     * 
     * @param id 活动表主键
     * @return 结果
     */
    public int deleteWakActivityById(Long id);

    /**
     * 批量删除活动表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakActivityByIds(Long[] ids);
}
