package com.ruoyi.wak.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawReq {

    private Long id;

    private String  address;

    private BigDecimal usdt;
}
