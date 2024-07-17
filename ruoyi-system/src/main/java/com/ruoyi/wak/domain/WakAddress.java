package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 控制地址对象 wak_address
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 密钥 */
    @Excel(name = "密钥")
    private String key;

    /** 状态，1启用，0不启用 */
    @Excel(name = "状态，1启用，0不启用")
    private Integer status;

    /** ETH/TRC余额 */
    @Excel(name = "ETH/TRC余额")
    private BigDecimal balance;

    /** USDT余额 */
    @Excel(name = "USDT余额")
    private BigDecimal balanceUsdt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setKey(String key) 
    {
        this.key = key;
    }

    public String getKey() 
    {
        return key;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setBalanceUsdt(BigDecimal balanceUsdt) 
    {
        this.balanceUsdt = balanceUsdt;
    }

    public BigDecimal getBalanceUsdt() 
    {
        return balanceUsdt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("address", getAddress())
            .append("key", getKey())
            .append("status", getStatus())
            .append("balance", getBalance())
            .append("balanceUsdt", getBalanceUsdt())
            .toString();
    }
}
