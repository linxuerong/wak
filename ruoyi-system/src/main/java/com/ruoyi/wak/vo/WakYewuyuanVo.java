package com.ruoyi.wak.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.wak.domain.WakYewuyuan;

public class WakYewuyuanVo extends WakYewuyuan {

    @Excel(name = "业务组")
    private String yewuzu;

    public String getYewuzu() {
        return yewuzu;
    }

    public void setYewuzu(String yewuzu) {
        this.yewuzu = yewuzu;
    }
}
