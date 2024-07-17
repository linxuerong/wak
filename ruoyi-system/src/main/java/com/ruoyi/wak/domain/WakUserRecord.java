package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户操作记录表对象 wak_user_record
 * 
 * @author ruoyi
 * @date 2024-06-04
 */
public class WakUserRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 原先eth */
    @Excel(name = "原先eth")
    private BigDecimal beforeEth;

    /** 之后eth */
    @Excel(name = "之后eth")
    private BigDecimal afterEth;

    /** 原先usdt */
    @Excel(name = "原先usdt")
    private BigDecimal beforeUsdt;

    /** 之后usdt */
    @Excel(name = "之后usdt")
    private BigDecimal afterUsdt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setBeforeEth(BigDecimal beforeEth) 
    {
        this.beforeEth = beforeEth;
    }

    public BigDecimal getBeforeEth() 
    {
        return beforeEth;
    }
    public void setAfterEth(BigDecimal afterEth) 
    {
        this.afterEth = afterEth;
    }

    public BigDecimal getAfterEth() 
    {
        return afterEth;
    }
    public void setBeforeUsdt(BigDecimal beforeUsdt) 
    {
        this.beforeUsdt = beforeUsdt;
    }

    public BigDecimal getBeforeUsdt() 
    {
        return beforeUsdt;
    }
    public void setAfterUsdt(BigDecimal afterUsdt) 
    {
        this.afterUsdt = afterUsdt;
    }

    public BigDecimal getAfterUsdt() 
    {
        return afterUsdt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("address", getAddress())
            .append("beforeEth", getBeforeEth())
            .append("afterEth", getAfterEth())
            .append("beforeUsdt", getBeforeUsdt())
            .append("afterUsdt", getAfterUsdt())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
