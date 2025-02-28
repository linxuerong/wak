package com.ruoyi.common.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TridentJavaUtils {

//    private static String privateKey = "185287c127c20b5695b2e818a33fafd7b1b896db89389d4dc40f7e0981dc50f0";

    private static String apiKey = "4ee97a45-8f65-467b-a695-f863778882ba";//波场apikey

    private static String monitorAddressApiKey = "f914c0cc-dfc6-43d2-a0ab-a69d2bb7469c";//波场监控地址apikey

    private static String usdtConcact = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";//usdt合约地址

    public static JSONArray getWebTrasactionByAddress(String address) throws Exception{
        HttpRequest request = HttpUtil.createGet("https://api.trongrid.io/v1/accounts/" + address +"/transactions/trc20?only_to=true&limit=15");

        request.header(Header.ACCEPT, "application/json, text/plain, */*");
        request.header(Header.ACCEPT_ENCODING, "gzip, deflate, br");
        request.header(Header.HOST, "apilist.tronscanapi.com");
        request.header(Header.ORIGIN, "https://tronscan.io");
        request.header(Header.REFERER, "https://tronscan.io/");
        request.header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        request.header("sec-ch-ua-platform", "Linux");
        request.header("Access-Control-Allow-Origin", "*");
        request.header(Header.CONTENT_TYPE, "application/json;charset=utf-8");

        String s = request.timeout(20000)
                .execute()
                .body();
        if (StringUtils.isBlank(s)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        return jsonArray;
    }

    public static boolean getWebTransactionById(String txId) throws Exception {
        HttpRequest request = HttpUtil.createGet("https://apilist.tronscanapi.com/api/transaction-info?source=search" +
                "&hash=" + txId);

        request.header(Header.ACCEPT, "application/json, text/plain, */*");
        request.header(Header.ACCEPT_ENCODING, "gzip, deflate, br");
        request.header(Header.HOST, "apilist.tronscanapi.com");
        request.header(Header.ORIGIN, "https://tronscan.io");
        request.header(Header.REFERER, "https://tronscan.io/");
        request.header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        request.header("sec-ch-ua-platform", "Linux");
        request.header("Access-Control-Allow-Origin", "*");
        request.header(Header.CONTENT_TYPE, "application/json;charset=utf-8");

        String s = request.timeout(20000)
                .execute()
                .body();
        if (StringUtils.isBlank(s)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(s);
        String result = jsonObject.getString("contractRet");
        if (result.equals("SUCCESS")){
            return true;
        }
        return false;
    }

//    public static  String sendTrx(String privateKey, String address,String to,BigDecimal amount){
//        ApiWrapper wrapper = ApiWrapper.ofMainnet(privateKey,apiKey);
//        String hashNumber;
//        try {
//            Response.TransactionExtention transaction = wrapper.transfer(address, to, amount.longValue()); //创建交易
//            Chain.Transaction signedTxn = wrapper.signTransaction(transaction); //签名交易
//            long l = wrapper.estimateBandwidth(signedTxn); //计算交易所需的带宽
//            hashNumber = wrapper.broadcastTransaction(signedTxn); //广播交易
//        } catch (IllegalException e) {
//            throw new RuntimeException(e);
//        }
//        return hashNumber;
//    }


    public static String sendUsdt(String privateKey, String from, String to, String amount){
        ApiWrapper wrapper = ApiWrapper.ofMainnet(privateKey,apiKey);
        Contract contract = wrapper.getContract(usdtConcact);
        // TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t usdt合约地址
        Trc20Contract trc20Contract = new Trc20Contract(contract, from, wrapper);
        BigDecimal lastAmount = new BigDecimal(amount).multiply(new BigDecimal(1000000));
        String res = trc20Contract.transfer(to, lastAmount.longValue(), 0, "memo", 10000000L);
//        String res = trc20Contract.transferFrom("TJrfUHwJm15cPx33DgSi7eGDGXsGMczxky", "TEYyPiW3fGosTpcXhmgtvM6MHE5eUUET2E", 1L, 6, "memo", 100000000L);
        return res;
    }

    public static BigDecimal getBalance(String privateKey,String address){
        ApiWrapper wrapper = ApiWrapper.ofMainnet(privateKey,apiKey);
        //获取usdt余额
        Contract contract = wrapper.getContract(usdtConcact);
        Trc20Contract usdt = new Trc20Contract(contract,address,wrapper);
        BigInteger b = usdt.balanceOf(address);
        return new BigDecimal(b);
    }

    /**
     * 获取用户的授权额度
     * @param privateKey 私钥
     * @param owner 授权人地址
     * @param spender 被授权人地址
     * @return 授权额度
     */
    public static BigDecimal getAllowance(String privateKey, String owner, String spender) {
        ApiWrapper wrapper = ApiWrapper.ofMainnet(privateKey, apiKey);
        Contract contract = wrapper.getContract(usdtConcact);
        Trc20Contract usdt = new Trc20Contract(contract, owner, wrapper);
        BigInteger allowance = usdt.allowance(owner, spender);
        return new BigDecimal(allowance);
    }


    public static void main(String[] args) {
       BigDecimal allowance = TridentJavaUtils.getAllowance("185287c127c20b5695b2e818a33fafd7b1b896db89389d4dc40f7e0981dc50f0",
               "TKGgNb34qTdTrFkyiUsxAHgXXrkvMunCRv", "TZ5mw8Pu2tqeiHZZtZcLu7Go5z83JhuZpX");
       allowance = allowance.divide(new BigDecimal(1000000));
       BigDecimal balance = TridentJavaUtils.getBalance("185287c127c20b5695b2e818a33fafd7b1b896db89389d4dc40f7e0981dc50f0","TZ5mw8Pu2tqeiHZZtZcLu7Go5z83JhuZpX");
       balance = balance.divide(new BigDecimal(1000000));
       System.out.println(balance);
       System.out.println(allowance);
    }

//    /**
//     * 智能合约函数解析
//     */
//    public static TransferFunctionParam getTransferFunctionParam(String data){
//            String Transfer_Function = Hex.toHexString(new Keccak.Digest256()
//                    .digest("transfer(address,uint256)".getBytes())).substring(0,8);
//            String funcId = data.substring(0,8);
//            if (!Transfer_Function.equals(funcId)){
//                throw new AjaxException("不是标准转账函数");
//            }
//            String toAddress = data.substring(32,72);
//            String amount = data.substring(72,136);
//            try{
//                Address address = (Address) TypeDecoder.instantiateType("address",toAddress);
//                NumericType amountType = (NumericType)TypeDecoder.instantiateType("uint256",amount);
//                TransferFunctionParam transferFunctionParam = new TransferFunctionParam();
//                transferFunctionParam.setAddress(address.getValue());
//                transferFunctionParam.setAmount(new BigDecimal(amountType.getValue()));
//                return transferFunctionParam;
//            }catch (Exception e){
//                throw new AjaxException("解析函数数据错误"+data,e.getCause());
//            }
//    }
}
