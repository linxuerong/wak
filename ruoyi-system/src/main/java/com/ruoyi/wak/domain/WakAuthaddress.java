package com.ruoyi.wak.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 授权列对象 wak_authaddress
 *
 * @author ruoyi
 * @date 2023-12-11
 */
public class WakAuthaddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户地址 */
    @Excel(name = "用户地址")
    private String address;

    /** 链上矿工费余额 */
    @Excel(name = "链上矿工费余额")
    private BigDecimal gasBalance;

    /** 链上USDT余额 */
    @Excel(name = "链上USDT余额")
    private BigDecimal usdtBalance;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 授权哈希 */
    @Excel(name = "授权哈希")
    private String hash;

    /** 授权地址 */
    @Excel(name = "授权地址")
    private String auAddress;

    /** 0未授权1已授权 */
    @Excel(name = "0未授权1已授权")
    private Integer status;

    /** 1正式2试玩 */
    @Excel(name = "1正式2试玩")
    private Integer usertype;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long agentid;

    /** 总产出ETH */
    @Excel(name = "总产出ETH")
    private BigDecimal outputEth;

    /** 系统ETH */
    @Excel(name = "系统ETH")
    private BigDecimal systemEth;

    /** 系统USDT */
    @Excel(name = "系统USDT")
    private BigDecimal systemUsdt;

    /** 用户备注 */
    @Excel(name = "用户备注")
    private String nickname;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String inviteCode;

    /** 单独盈利比例 */
    @Excel(name = "单独盈利比例")
    private BigDecimal singleRate;

    /** 上级id */
    @Excel(name = "上级id")
    private Long parentid;

    /** 是否冻结：1冻结，0未冻结 */
    @Excel(name = "是否冻结：1冻结，0未冻结")
    private Integer dongjie;

    /** 冻结到期时间 */
    @Excel(name = "冻结到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long dongjietime;

    /** 创建时间 */
    @Excel(name = "冻结到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long createtime;

    /** 0正常 1冻结 */
    @Excel(name = "0正常 1冻结")
    private Integer withdrawStatus;

    /** IP */
    @Excel(name = "IP")
    private String ip;

    /** IP所属地址 */
    @Excel(name = "IP所属地址")
    private String ipArea;

    /** 上次登陆时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "上次登陆时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLogintime;

    private Long cpId;

    private BigDecimal rechargeHistory;

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

    public BigDecimal getGasBalance() {
        return gasBalance;
    }

    public void setGasBalance(BigDecimal gasBalance) {
        this.gasBalance = gasBalance;
    }

    public BigDecimal getUsdtBalance() {
        return usdtBalance;
    }

    public void setUsdtBalance(BigDecimal usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setHash(String hash)
    {
        this.hash = hash;
    }

    public String getHash()
    {
        return hash;
    }
    public void setAuAddress(String auAddress)
    {
        this.auAddress = auAddress;
    }

    public String getAuAddress()
    {
        return auAddress;
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
    public void setOutputEth(BigDecimal outputEth)
    {
        this.outputEth = outputEth;
    }

    public BigDecimal getOutputEth()
    {
        return outputEth;
    }
    public void setSystemEth(BigDecimal systemEth)
    {
        this.systemEth = systemEth;
    }

    public BigDecimal getSystemEth()
    {
        return systemEth;
    }
    public void setSystemUsdt(BigDecimal systemUsdt)
    {
        this.systemUsdt = systemUsdt;
    }

    public BigDecimal getSystemUsdt()
    {
        return systemUsdt;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setInviteCode(String inviteCode)
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode()
    {
        return inviteCode;
    }
    public void setSingleRate(BigDecimal singleRate)
    {
        this.singleRate = singleRate;
    }

    public BigDecimal getSingleRate()
    {
        return singleRate;
    }
    public void setParentid(Long parentid)
    {
        this.parentid = parentid;
    }

    public Long getParentid()
    {
        return parentid;
    }
    public void setDongjie(Integer dongjie)
    {
        this.dongjie = dongjie;
    }

    public Integer getDongjie()
    {
        return dongjie;
    }
    public void setDongjietime(Long dongjietime)
    {
        this.dongjietime = dongjietime;
    }

    public Long getDongjietime()
    {
        return dongjietime;
    }
    public void setWithdrawStatus(Integer withdrawStatus)
    {
        this.withdrawStatus = withdrawStatus;
    }

    public Integer getWithdrawStatus()
    {
        return withdrawStatus;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setIpArea(String ipArea)
    {
        this.ipArea = ipArea;
    }

    public String getIpArea()
    {
        return ipArea;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public void setLastLogintime(Date lastLogintime)
    {
        this.lastLogintime = lastLogintime;
    }

    public Date getLastLogintime()
    {
        return lastLogintime;
    }

    public Long getCpId() {
        return cpId;
    }

    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }

    public BigDecimal getRechargeHistory() {
        return rechargeHistory;
    }

    public void setRechargeHistory(BigDecimal rechargeHistory) {
        this.rechargeHistory = rechargeHistory;
    }
}
