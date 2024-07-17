package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户ETH转换日志对象 wak_exchangelog
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakExchangelog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** ETH数量 */
    @Excel(name = "ETH数量")
    private BigDecimal ethNum;

    /** ETH时价 */
    @Excel(name = "ETH时价")
    private BigDecimal ethPrice;

    /** 兑换USDT数量 */
    @Excel(name = "兑换USDT数量")
    private BigDecimal usdtNum;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 用户id */
    @Excel(name = "创建时间")
    private Long createtime;

    /** ETH原先数量 */
    @Excel(name = "ETH原先数量")
    private BigDecimal ethBeforeBalance;

    /** ETH最后数量 */
    @Excel(name = "ETH最后数量")
    private BigDecimal ethLastBalance;

    /** USDT原先数量 */
    @Excel(name = "USDT原先数量")
    private BigDecimal usdtBeforeBalance;

    /** USDT最后数量 */
    @Excel(name = "USDT最后数量")
    private BigDecimal usdtLastBalance;

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
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setEthNum(BigDecimal ethNum) 
    {
        this.ethNum = ethNum;
    }

    public BigDecimal getEthNum() 
    {
        return ethNum;
    }
    public void setEthPrice(BigDecimal ethPrice) 
    {
        this.ethPrice = ethPrice;
    }

    public BigDecimal getEthPrice() 
    {
        return ethPrice;
    }
    public void setUsdtNum(BigDecimal usdtNum) 
    {
        this.usdtNum = usdtNum;
    }

    public BigDecimal getUsdtNum() 
    {
        return usdtNum;
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

    public BigDecimal getEthBeforeBalance() {
        return ethBeforeBalance;
    }

    public void setEthBeforeBalance(BigDecimal ethBeforeBalance) {
        this.ethBeforeBalance = ethBeforeBalance;
    }

    public BigDecimal getEthLastBalance() {
        return ethLastBalance;
    }

    public void setEthLastBalance(BigDecimal ethLastBalance) {
        this.ethLastBalance = ethLastBalance;
    }

    public BigDecimal getUsdtBeforeBalance() {
        return usdtBeforeBalance;
    }

    public void setUsdtBeforeBalance(BigDecimal usdtBeforeBalance) {
        this.usdtBeforeBalance = usdtBeforeBalance;
    }

    public BigDecimal getUsdtLastBalance() {
        return usdtLastBalance;
    }

    public void setUsdtLastBalance(BigDecimal usdtLastBalance) {
        this.usdtLastBalance = usdtLastBalance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
