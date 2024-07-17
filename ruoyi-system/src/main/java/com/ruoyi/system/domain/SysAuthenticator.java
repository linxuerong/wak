package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 谷歌验证对象 sys_authenticator
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
public class SysAuthenticator extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 谷歌密钥 */
    @Excel(name = "谷歌密钥")
    private String googleKey;

    /** 0显示 1隐藏 */
    @Excel(name = "0显示 1隐藏")
    private Integer isShow;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setGoogleKey(String googleKey) 
    {
        this.googleKey = googleKey;
    }

    public String getGoogleKey() 
    {
        return googleKey;
    }
    public void setIsShow(Integer isShow) 
    {
        this.isShow = isShow;
    }

    public Integer getIsShow() 
    {
        return isShow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("googleKey", getGoogleKey())
            .append("isShow", getIsShow())
            .toString();
    }
}
