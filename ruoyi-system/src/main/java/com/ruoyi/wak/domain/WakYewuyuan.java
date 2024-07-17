package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务员对象 wak_yewuyuan
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public class WakYewuyuan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 业务员 */
    @Excel(name = "业务员")
    private String yewuyuan;

    /** 业务组id */
    @Excel(name = "业务组id")
    private Long yewuzuid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setYewuyuan(String yewuyuan) 
    {
        this.yewuyuan = yewuyuan;
    }

    public String getYewuyuan() 
    {
        return yewuyuan;
    }
    public void setYewuzuid(Long yewuzuid) 
    {
        this.yewuzuid = yewuzuid;
    }

    public Long getYewuzuid() 
    {
        return yewuzuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yewuyuan", getYewuyuan())
            .append("yewuzuid", getYewuzuid())
            .toString();
    }
}
