package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产出分润表对象 wak_outputlog_reward
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
public class WakOutputlogReward extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** 被邀请者的地址 */
    @Excel(name = "被邀请者的地址")
    private String rewardAddress;

    /** 奖励的eth */
    @Excel(name = "奖励的eth")
    private BigDecimal rewardEth;

    /** 被邀请者的id */
    @Excel(name = "被邀请者的id")
    private Long rewardUid;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    @Excel(name = "创建时间")
    private Long createtime;

    /** 当日等值usdt */
    @Excel(name = "当日等值usdt")
    private BigDecimal beUsdt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setRewardAddress(String rewardAddress) 
    {
        this.rewardAddress = rewardAddress;
    }

    public String getRewardAddress() 
    {
        return rewardAddress;
    }
    public void setRewardEth(BigDecimal rewardEth) 
    {
        this.rewardEth = rewardEth;
    }

    public BigDecimal getRewardEth() 
    {
        return rewardEth;
    }
    public void setRewardUid(Long rewardUid) 
    {
        this.rewardUid = rewardUid;
    }

    public Long getRewardUid() 
    {
        return rewardUid;
    }
    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public BigDecimal getBeUsdt() {
        return beUsdt;
    }

    public void setBeUsdt(BigDecimal beUsdt) {
        this.beUsdt = beUsdt;
    }
}
