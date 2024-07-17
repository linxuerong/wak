package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakActivity;
import com.ruoyi.wak.domain.WakAuthaddress;

/**
 * 活动表Service接口
 *
 * @author ruoyi
 * @date 2024-07-15
 */
public interface IWakActivityService
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
     * 批量删除活动表
     *
     * @param ids 需要删除的活动表主键集合
     * @return 结果
     */
    public int deleteWakActivityByIds(Long[] ids);

    /**
     * 删除活动表信息
     *
     * @param id 活动表主键
     * @return 结果
     */
    public int deleteWakActivityById(Long id);

    List getMyActivityInfo(WakAuthaddress wakAuthaddress, int activityType);

    int receiveMyActivityInfo(WakAuthaddress wakAuthaddress, WakActivity wakActivity);
}
