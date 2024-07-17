package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务组对象 wak_yewuzu
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public class WakYewuzu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 业务组 */
    @Excel(name = "业务组")
    private String yewuzu;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setYewuzu(String yewuzu) 
    {
        this.yewuzu = yewuzu;
    }

    public String getYewuzu() 
    {
        return yewuzu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yewuzu", getYewuzu())
            .toString();
    }
}
