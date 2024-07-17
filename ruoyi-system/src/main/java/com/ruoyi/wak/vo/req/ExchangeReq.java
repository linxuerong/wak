package com.ruoyi.wak.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeReq {

    private Long id;

    private String address;

    private BigDecimal eth;
}
