package com.ruoyi.web.controller;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class TrxBalance {
    public static void main(String[] args) {

        String res = HttpUtils.sendGet("https://api.trongrid.io/v1/accounts/TWC2xN177gL9F8WuJUfNriUtwjt3t8m1cF"); //接口地址
        JSONObject jsonObject = JSONObject.parseObject(res);
        if (jsonObject.getString("success").equals("true")){
            JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
            BigDecimal trxBalance = data.getBigDecimal("balance").divide(new BigDecimal(1000000),2, RoundingMode.HALF_DOWN);
            System.out.println(trxBalance);
        }
    }
}
