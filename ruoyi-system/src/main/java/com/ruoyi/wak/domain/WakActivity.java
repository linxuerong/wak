package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 活动表对象 wak_activity
 *
 * @author ruoyi
 * @date 2024-07-15
 */
public class WakActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String activityName;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 活动金额 */
    @Excel(name = "活动金额")
    private BigDecimal targetAmount;

    /** 附加活动金额 */
    @Excel(name = "附加活动金额")
    private BigDecimal additionalTargetAmount;

    /** 奖励金额 */
    @Excel(name = "奖励金额")
    private BigDecimal rewardAmount;

    /** 档位 */
    @Excel(name = "档位")
    private Integer level;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getActivityName()
    {
        return activityName;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public Integer getLevel()
    {
        return level;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getAdditionalTargetAmount() {
        return additionalTargetAmount;
    }

    public void setAdditionalTargetAmount(BigDecimal additionalTargetAmount) {
        this.additionalTargetAmount = additionalTargetAmount;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activityName", getActivityName())
            .append("type", getType())
            .append("targetAmount", getTargetAmount())
            .append("additional additionalTargetAmount", getAdditionalTargetAmount())
            .append("rewardAmount", getRewardAmount())
            .append("level", getLevel())
            .toString();
    }
}
