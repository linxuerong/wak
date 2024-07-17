package com.ruoyi.wak.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 统计对象 wak_tongji
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public class WakTongji extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 业务组 */
    @Excel(name = "业务组")
    private String yewuzu;

    /** 业务员 */
    @Excel(name = "业务员")
    private String yewuyuan;

    /** 总充值金额 */
    @Excel(name = "总充值金额")
    private String recharge;

    /** 总提现金额 */
    @Excel(name = "总提现金额")
    private String withdraw;

    @Excel(name = "创建时间")
    private String createtime;

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
    public void setYewuyuan(String yewuyuan) 
    {
        this.yewuyuan = yewuyuan;
    }

    public String getYewuyuan() 
    {
        return yewuyuan;
    }
    public void setRecharge(String recharge) 
    {
        this.recharge = recharge;
    }

    public String getRecharge() 
    {
        return recharge;
    }
    public void setWithdraw(String withdraw) 
    {
        this.withdraw = withdraw;
    }

    public String getWithdraw() 
    {
        return withdraw;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yewuzu", getYewuzu())
            .append("yewuyuan", getYewuyuan())
            .append("recharge", getRecharge())
            .append("withdraw", getWithdraw())
            .append("createtime", getCreatetime())
            .toString();
    }
}
