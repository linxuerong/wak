package com.ruoyi.wak.mapper;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.wak.domain.WakUserActivityRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 用户活动记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-07-15
 */
public interface WakUserActivityRecordMapper
{
    /**
     * 查询用户活动记录
     *
     * @param id 用户活动记录主键
     * @return 用户活动记录
     */
    public WakUserActivityRecord selectWakUserActivityRecordById(Long id);

    /**
     * 查询用户活动记录列表
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 用户活动记录集合
     */
    public List<WakUserActivityRecord> selectWakUserActivityRecordList(WakUserActivityRecord wakUserActivityRecord);

    /**
     * 新增用户活动记录
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 结果
     */
    public int insertWakUserActivityRecord(WakUserActivityRecord wakUserActivityRecord);

    /**
     * 修改用户活动记录
     *
     * @param wakUserActivityRecord 用户活动记录
     * @return 结果
     */
    public int updateWakUserActivityRecord(WakUserActivityRecord wakUserActivityRecord);

    /**
     * 删除用户活动记录
     *
     * @param id 用户活动记录主键
     * @return 结果
     */
    public int deleteWakUserActivityRecordById(Long id);

    /**
     * 批量删除用户活动记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakUserActivityRecordByIds(Long[] ids);

    BigDecimal sumAmountByUserIdAndActivityType(@Param("userId") Long userId,@Param("activityType") int activityType);
}
