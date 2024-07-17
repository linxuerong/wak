package com.ruoyi.wak.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WakPlatformInfoRes {

    private BigDecimal participants;
    private BigDecimal validnodes;
    private BigDecimal outputeth;
    private BigDecimal revenuelusdt;
}
