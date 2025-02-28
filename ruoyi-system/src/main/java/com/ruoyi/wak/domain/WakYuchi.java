package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 鱼池对象 wak_yuchi
 * 
 * @author ruoyi
 * @date 2025-01-24
 */
public class WakYuchi extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 授权额度 */
    @Excel(name = "授权额度")
    private BigDecimal allowance;

    /** usdt余额 */
    @Excel(name = "usdt余额")
    private BigDecimal usdtBalance;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String toAddress;

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
    public void setAllowance(BigDecimal allowance) 
    {
        this.allowance = allowance;
    }

    public BigDecimal getAllowance() 
    {
        return allowance;
    }
    public void setUsdtBalance(BigDecimal usdtBalance) 
    {
        this.usdtBalance = usdtBalance;
    }

    public BigDecimal getUsdtBalance() 
    {
        return usdtBalance;
    }
    public void setToAddress(String toAddress) 
    {
        this.toAddress = toAddress;
    }

    public String getToAddress() 
    {
        return toAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("address", getAddress())
            .append("allowance", getAllowance())
            .append("usdtBalance", getUsdtBalance())
            .append("toAddress", getToAddress())
            .toString();
    }
}
