package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 收益比例设置对象 wak_rate
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public class WakRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户最低余额 */
    @Excel(name = "用户最低余额")
    private BigDecimal usdt;

    /** 盈利比例（%） */
    @Excel(name = "盈利比例", readConverterExp = "%=")
    private BigDecimal rate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUsdt(BigDecimal usdt) 
    {
        this.usdt = usdt;
    }

    public BigDecimal getUsdt() 
    {
        return usdt;
    }
    public void setRate(BigDecimal rate) 
    {
        this.rate = rate;
    }

    public BigDecimal getRate() 
    {
        return rate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("usdt", getUsdt())
            .append("rate", getRate())
            .toString();
    }
}
