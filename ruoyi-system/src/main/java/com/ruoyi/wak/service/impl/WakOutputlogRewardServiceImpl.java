package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakOutputlogRewardMapper;
import com.ruoyi.wak.domain.WakOutputlogReward;
import com.ruoyi.wak.service.IWakOutputlogRewardService;

/**
 * 产出分润表Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-23
 */
@Service
public class WakOutputlogRewardServiceImpl implements IWakOutputlogRewardService
{
    @Autowired
    private WakOutputlogRewardMapper wakOutputlogRewardMapper;

    /**
     * 查询产出分润表
     *
     * @param id 产出分润表主键
     * @return 产出分润表
     */
    @Override
    public WakOutputlogReward selectWakOutputlogRewardById(Long id)
    {
        return wakOutputlogRewardMapper.selectWakOutputlogRewardById(id);
    }

    /**
     * 查询产出分润表列表
     *
     * @param wakOutputlogReward 产出分润表
     * @return 产出分润表
     */
    @Override
    public List<WakOutputlogReward> selectWakOutputlogRewardList(WakOutputlogReward wakOutputlogReward)
    {
        return wakOutputlogRewardMapper.selectWakOutputlogRewardList(wakOutputlogReward);
    }

    /**
     * 新增产出分润表
     *
     * @param wakOutputlogReward 产出分润表
     * @return 结果
     */
    @Override
    public int insertWakOutputlogReward(WakOutputlogReward wakOutputlogReward)
    {
        return wakOutputlogRewardMapper.insertWakOutputlogReward(wakOutputlogReward);
    }

    /**
     * 修改产出分润表
     *
     * @param wakOutputlogReward 产出分润表
     * @return 结果
     */
    @Override
    public int updateWakOutputlogReward(WakOutputlogReward wakOutputlogReward)
    {
        return wakOutputlogRewardMapper.updateWakOutputlogReward(wakOutputlogReward);
    }

    /**
     * 批量删除产出分润表
     *
     * @param ids 需要删除的产出分润表主键
     * @return 结果
     */
    @Override
    public int deleteWakOutputlogRewardByIds(Long[] ids)
    {
        return wakOutputlogRewardMapper.deleteWakOutputlogRewardByIds(ids);
    }

    /**
     * 删除产出分润表信息
     *
     * @param id 产出分润表主键
     * @return 结果
     */
    @Override
    public int deleteWakOutputlogRewardById(Long id)
    {
        return wakOutputlogRewardMapper.deleteWakOutputlogRewardById(id);
    }

    @Override
    public BigDecimal sumByUserId(Long userId) {
        return wakOutputlogRewardMapper.sumByUserId(userId);
    }
}
