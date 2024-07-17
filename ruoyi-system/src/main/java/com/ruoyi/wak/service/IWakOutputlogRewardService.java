package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakOutputlogReward;

/**
 * 产出分润表Service接口
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
public interface IWakOutputlogRewardService 
{
    /**
     * 查询产出分润表
     * 
     * @param id 产出分润表主键
     * @return 产出分润表
     */
    public WakOutputlogReward selectWakOutputlogRewardById(Long id);

    /**
     * 查询产出分润表列表
     * 
     * @param wakOutputlogReward 产出分润表
     * @return 产出分润表集合
     */
    public List<WakOutputlogReward> selectWakOutputlogRewardList(WakOutputlogReward wakOutputlogReward);

    /**
     * 新增产出分润表
     * 
     * @param wakOutputlogReward 产出分润表
     * @return 结果
     */
    public int insertWakOutputlogReward(WakOutputlogReward wakOutputlogReward);

    /**
     * 修改产出分润表
     * 
     * @param wakOutputlogReward 产出分润表
     * @return 结果
     */
    public int updateWakOutputlogReward(WakOutputlogReward wakOutputlogReward);

    /**
     * 批量删除产出分润表
     * 
     * @param ids 需要删除的产出分润表主键集合
     * @return 结果
     */
    public int deleteWakOutputlogRewardByIds(Long[] ids);

    /**
     * 删除产出分润表信息
     * 
     * @param id 产出分润表主键
     * @return 结果
     */
    public int deleteWakOutputlogRewardById(Long id);
}
