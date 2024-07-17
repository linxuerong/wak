package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 平台配置对象 wak_config
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public class WakConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 变量名 */
    @Excel(name = "变量名")
    private String name;

    /** 分组 */
    @Excel(name = "分组")
    private String group;

    /** 变量标题 */
    @Excel(name = "变量标题")
    private String title;

    /** 变量描述 */
    @Excel(name = "变量描述")
    private String tip;

    /** 类型:string,text,int,bool,array,datetime,date,file */
    @Excel(name = "类型:string,text,int,bool,array,datetime,date,file")
    private String type;

    /** 可见条件 */
    @Excel(name = "可见条件")
    private String visible;

    /** 变量值 */
    @Excel(name = "变量值")
    private String value;

    /** 变量字典数据 */
    @Excel(name = "变量字典数据")
    private String content;

    /** 验证规则 */
    @Excel(name = "验证规则")
    private String rule;

    /** 扩展属性 */
    @Excel(name = "扩展属性")
    private String extend;

    /** 配置 */
    @Excel(name = "配置")
    private String setting;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGroup(String group) 
    {
        this.group = group;
    }

    public String getGroup() 
    {
        return group;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setTip(String tip) 
    {
        this.tip = tip;
    }

    public String getTip() 
    {
        return tip;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setVisible(String visible) 
    {
        this.visible = visible;
    }

    public String getVisible() 
    {
        return visible;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setRule(String rule) 
    {
        this.rule = rule;
    }

    public String getRule() 
    {
        return rule;
    }
    public void setExtend(String extend) 
    {
        this.extend = extend;
    }

    public String getExtend() 
    {
        return extend;
    }
    public void setSetting(String setting) 
    {
        this.setting = setting;
    }

    public String getSetting() 
    {
        return setting;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("group", getGroup())
            .append("title", getTitle())
            .append("tip", getTip())
            .append("type", getType())
            .append("visible", getVisible())
            .append("value", getValue())
            .append("content", getContent())
            .append("rule", getRule())
            .append("extend", getExtend())
            .append("setting", getSetting())
            .toString();
    }
}
