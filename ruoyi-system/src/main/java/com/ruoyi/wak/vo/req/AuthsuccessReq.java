package com.ruoyi.wak.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AuthsuccessReq {

    private Long id;

    private String address;

    private BigDecimal usdt_balance;

    private String type;

    private String hash;

    private String au_address;
}
