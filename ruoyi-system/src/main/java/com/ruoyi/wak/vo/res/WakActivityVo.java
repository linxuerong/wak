package com.ruoyi.wak.vo.res;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

public class WakActivityVo {
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

    private Integer targetStatus;//接收状态0未达标 1未领取 2已领取

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

    public Integer getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Integer targetStatus) {
        this.targetStatus = targetStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
