package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户产出收益日志对象 wak_outputlog
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakOutputlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** 当前挖矿USDT */
    @Excel(name = "当前挖矿USDT")
    private BigDecimal systemUsdt;

    /** 产出eth */
    @Excel(name = "产出eth")
    private BigDecimal outputEth;

    /** 盈利比例% */
    @Excel(name = "盈利比例%")
    private BigDecimal rate;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 用户备注 */
    @Excel(name = "用户备注")
    private String nickname;

    @Excel(name = "创建时间")
    private Long createtime;
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
    public void setSystemUsdt(BigDecimal systemUsdt) 
    {
        this.systemUsdt = systemUsdt;
    }

    public BigDecimal getSystemUsdt() 
    {
        return systemUsdt;
    }
    public void setOutputEth(BigDecimal outputEth) 
    {
        this.outputEth = outputEth;
    }

    public BigDecimal getOutputEth() 
    {
        return outputEth;
    }
    public void setRate(BigDecimal rate) 
    {
        this.rate = rate;
    }

    public BigDecimal getRate() 
    {
        return rate;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
