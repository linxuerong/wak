package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakUserActivityRecordMapper;
import com.ruoyi.wak.domain.WakUserActivityRecord;
import com.ruoyi.wak.service.IWakUserActivityRecordService;

/**
 * 用户活动记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-07-15
 */
@Service
public class WakUserActivityRecordServiceImpl implements IWakUserActivityRecordService
{
    @Autowired
    private WakUserActivityRecordMapper wakUserActivityRecordMapper;

    /**
     * 查询用户活动记录
     *
     * @param id 用户活动记录主键
     * @return 用户活动记录
     */
    @Override
    public WakUserActivityRecord selectWakUserActivityRecordById(Long id)
    {
        return wakUserActivityRecordMapper.selectWakUserActivityRecordById(id);
    }

    /**
     * 查询用户活动记录列表
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 用户活动记录
     */
    @Override
    public List<WakUserActivityRecord> selectWakUserActivityRecordList(WakUserActivityRecord wakUserActivityRecord)
    {
        return wakUserActivityRecordMapper.selectWakUserActivityRecordList(wakUserActivityRecord);
    }

    /**
     * 新增用户活动记录
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 结果
     */
    @Override
    public int insertWakUserActivityRecord(WakUserActivityRecord wakUserActivityRecord)
    {
        wakUserActivityRecord.setCreateTime(DateUtils.getNowDate());
        return wakUserActivityRecordMapper.insertWakUserActivityRecord(wakUserActivityRecord);
    }

    /**
     * 修改用户活动记录
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 结果
     */
    @Override
    public int updateWakUserActivityRecord(WakUserActivityRecord wakUserActivityRecord)
    {
        return wakUserActivityRecordMapper.updateWakUserActivityRecord(wakUserActivityRecord);
    }

    /**
     * 批量删除用户活动记录
     *
     * @param ids 需要删除的用户活动记录主键
     * @return 结果
     */
    @Override
    public int deleteWakUserActivityRecordByIds(Long[] ids)
    {
        return wakUserActivityRecordMapper.deleteWakUserActivityRecordByIds(ids);
    }

    /**
     * 删除用户活动记录信息
     *
     * @param id 用户活动记录主键
     * @return 结果
     */
    @Override
    public int deleteWakUserActivityRecordById(Long id)
    {
        return wakUserActivityRecordMapper.deleteWakUserActivityRecordById(id);
    }

    @Override
    public BigDecimal sumAmountByUserIdAndActivityType(Long userId, int activityType) {
        return wakUserActivityRecordMapper.sumAmountByUserIdAndActivityType(userId,activityType);
    }
}
