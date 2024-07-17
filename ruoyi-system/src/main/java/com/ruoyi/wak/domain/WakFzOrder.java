package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 质押订单对象 wak_fz_order
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
public class WakFzOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** 质押要求 */
    @Excel(name = "质押要求")
    private BigDecimal fzUsdtLimit;

    /** 质押周期（天） */
    @Excel(name = "质押周期", readConverterExp = "天=")
    private Integer fzDay;

    /** 已质押usdt */
    @Excel(name = "已质押usdt")
    private BigDecimal  fzUsdt;

    /** 挖矿收益eth */
    @Excel(name = "挖矿收益eth")
    private BigDecimal wkReward;

    /** 挖矿间隔（分钟） */
    @Excel(name = "挖矿间隔", readConverterExp = "分钟")
    private Integer wkLimit;

    /** 产矿比例（最多3位小数） */
    @Excel(name = "产矿比例", readConverterExp = "最多3位小数")
    private BigDecimal rewardRate;

    /** 完成奖励（最多6位小数） */
    @Excel(name = "完成奖励", readConverterExp = "最多6位小数")
    private BigDecimal finishReward;

    /** 自动结算（0否 1是） */
    @Excel(name = "自动结算", readConverterExp = "0=否,1=是")
    private Integer isAuto;

    /** 质押状态（0发送邀请，1进行中，2已结束） */
    @Excel(name = "质押状态", readConverterExp = "0=发送邀请，1进行中，2已结束")
    private Integer status;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "MM-dd yyyy")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 用户备注 */
    @Excel(name = "用户备注")
    private String nickname;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setFzUsdtLimit(BigDecimal fzUsdtLimit) 
    {
        this.fzUsdtLimit = fzUsdtLimit;
    }

    public BigDecimal getFzUsdtLimit() 
    {
        return fzUsdtLimit;
    }
    public void setFzDay(Integer fzDay) 
    {
        this.fzDay = fzDay;
    }

    public Integer getFzDay() 
    {
        return fzDay;
    }

    public BigDecimal getFzUsdt() {
        return fzUsdt;
    }

    public void setFzUsdt(BigDecimal fzUsdt) {
        this.fzUsdt = fzUsdt;
    }

    public void setWkReward(BigDecimal wkReward)
    {
        this.wkReward = wkReward;
    }

    public BigDecimal getWkReward() 
    {
        return wkReward;
    }
    public void setWkLimit(Integer wkLimit) 
    {
        this.wkLimit = wkLimit;
    }

    public Integer getWkLimit() 
    {
        return wkLimit;
    }
    public void setRewardRate(BigDecimal rewardRate) 
    {
        this.rewardRate = rewardRate;
    }

    public BigDecimal getRewardRate() 
    {
        return rewardRate;
    }
    public void setFinishReward(BigDecimal finishReward) 
    {
        this.finishReward = finishReward;
    }

    public BigDecimal getFinishReward() 
    {
        return finishReward;
    }
    public void setIsAuto(Integer isAuto) 
    {
        this.isAuto = isAuto;
    }

    public Integer getIsAuto() 
    {
        return isAuto;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
