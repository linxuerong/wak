package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 转账记录对象 wak_transferlog
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakTransferlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** USDT金额 */
    @Excel(name = "USDT金额")
    private String usdt;

    /** 交易哈希 */
    @Excel(name = "交易哈希")
    private String hash;

    /** 收款地址 */
    @Excel(name = "收款地址")
    private String shouAddress;

    /** 1成功0失败 */
    @Excel(name = "1成功0失败")
    private Long status;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long agentid;

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
    public void setUsdt(String usdt) 
    {
        this.usdt = usdt;
    }

    public String getUsdt() 
    {
        return usdt;
    }
    public void setHash(String hash) 
    {
        this.hash = hash;
    }

    public String getHash() 
    {
        return hash;
    }
    public void setShouAddress(String shouAddress) 
    {
        this.shouAddress = shouAddress;
    }

    public String getShouAddress() 
    {
        return shouAddress;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setAgentid(Long agentid) 
    {
        this.agentid = agentid;
    }

    public Long getAgentid() 
    {
        return agentid;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("address", getAddress())
            .append("usdt", getUsdt())
            .append("hash", getHash())
            .append("shouAddress", getShouAddress())
            .append("status", getStatus())
            .append("createtime", getCreatetime())
            .append("agentid", getAgentid())
            .toString();
    }
}
