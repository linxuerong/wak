package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户提现日志对象 wak_withdrawlog
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakWithdrawlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** 提现USDT数量 */
    @Excel(name = "提现USDT数量")
    private BigDecimal usdt;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 用户身份:1=正式,2=试玩 */
    @Excel(name = "用户身份:1=正式,2=试玩")
    private Integer usertype;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long agentid;

    /** 用户id */
    @Excel(name = "用户id")
    private Long uid;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickname;

    @Excel(name = "创建时间")
    private Long createtime;

    @Excel(name = "审核时间")
    private Long updatetime;

    private BigDecimal systemUsdt;

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
    public void setUsdt(BigDecimal usdt) 
    {
        this.usdt = usdt;
    }

    public BigDecimal getUsdt() 
    {
        return usdt;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setUsertype(Integer usertype) 
    {
        this.usertype = usertype;
    }

    public Integer getUsertype() 
    {
        return usertype;
    }
    public void setAgentid(Long agentid) 
    {
        this.agentid = agentid;
    }

    public Long getAgentid() 
    {
        return agentid;
    }
    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }
    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public BigDecimal getSystemUsdt() {
        return systemUsdt;
    }

    public void setSystemUsdt(BigDecimal systemUsdt) {
        this.systemUsdt = systemUsdt;
    }
}
