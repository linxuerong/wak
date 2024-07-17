package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 用户活动记录对象 wak_user_activity_record
 *
 * @author ruoyi
 * @date 2024-07-15
 */
public class WakUserActivityRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 活动Id */
    @Excel(name = "活动Id")
    private Long activityId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String userAddress;

    /** 领取的金额 */
    @Excel(name = "领取的金额")
    private BigDecimal rewardAmount;

    /** 活动类型 */
    @Excel(name = "活动类型")
    private Integer activyType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setActivityId(Long activityId)
    {
        this.activityId = activityId;
    }

    public Long getActivityId()
    {
        return activityId;
    }
    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityName()
    {
        return activityName;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public void setActivyType(Integer activyType)
    {
        this.activyType = activyType;
    }

    public Integer getActivyType()
    {
        return activyType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("userId", getUserId())
            .append("userAddress", getUserAddress())
            .append("rewardAmount", getRewardAmount())
            .append("createTime", getCreateTime())
            .append("activyType", getActivyType())
            .toString();
    }
}
