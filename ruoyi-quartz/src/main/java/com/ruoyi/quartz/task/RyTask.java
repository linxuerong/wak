package com.ruoyi.quartz.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.wak.domain.*;
import com.ruoyi.wak.service.*;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.proto.Response;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IWakConfigService wakConfigService;

    @Autowired
    private IWakFzOrderService wakFzOrderService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    @Autowired
    private IWakOutputlogService wakOutputlogService;

    @Autowired
    private IWakRateService wakRateService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IWakAddressService wakAddressService;

    @Autowired
    private IWakAddresschuService wakAddresschuService;

    @Autowired
    private IWakOutputlogRewardService wakOutputlogRewardService;


    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void updatePlatformInfo(){
        WakConfig participants = wakConfigService.selectWakConfigByName("participants");
        int randomparticipants = RandomUtils.nextInt(50,100);
        BigDecimal newParticipants = new BigDecimal(participants.getValue()).add(new BigDecimal(randomparticipants));
        participants.setValue(newParticipants.toString());
        wakConfigService.updateWakConfig(participants);

        WakConfig validnodes = wakConfigService.selectWakConfigByName("validnodes");
        int randomvalidnodes = RandomUtils.nextInt(5,10);
        BigDecimal newvalidnodes = new BigDecimal(validnodes.getValue()).add(new BigDecimal(randomvalidnodes));
        validnodes.setValue(newvalidnodes.toString());
        wakConfigService.updateWakConfig(validnodes);

        WakConfig outputeth = wakConfigService.selectWakConfigByName("outputeth");
        BigDecimal newoutputeth = new BigDecimal(outputeth.getValue()).multiply(new BigDecimal(0.03)).setScale(0,RoundingMode.HALF_UP);

        outputeth.setValue(new BigDecimal(outputeth.getValue()).add(newoutputeth).toString());
        wakConfigService.updateWakConfig(outputeth);


        WakConfig revenuelusdt = wakConfigService.selectWakConfigByName("revenuelusdt");
        BigDecimal newrevenuelusdt = new BigDecimal(revenuelusdt.getValue()).multiply(new BigDecimal(0.03)).setScale(0,RoundingMode.HALF_UP);
        revenuelusdt.setValue(new BigDecimal(revenuelusdt.getValue()).add(newrevenuelusdt).toString());
        wakConfigService.updateWakConfig(revenuelusdt);
    }

    public void wk(){
        Date date = new Date();
        WakFzOrder param = new WakFzOrder();
        param.setStatus(1);
        String markpX = redisCache.getCacheObject("ehtPrice");
        if (StringUtils.isBlank(markpX)){
            String url = "https://www.okx.com/api/v5/public/mark-price?instType=SWAP&instId=ETH-USDT-SWAP";
            String res = HttpUtils.sendGet(url);
            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject data = jsonArray.getJSONObject(0);
            markpX= data.getString("markPx");
            redisCache.setCacheObject("ehtPrice",markpX,30, TimeUnit.SECONDS);
        }

        //质押产出
        List<WakFzOrder> dealList = wakFzOrderService.selectWakFzOrderList(param);
        for (WakFzOrder wakFzOrder:
            dealList) {
            WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakFzOrder.getUid());
            if (wakAuthaddress==null){
                wakFzOrder.setStatus(1);
                wakFzOrderService.updateWakFzOrder(wakFzOrder);
                continue;
            }
            //判断是否到期
            if (wakFzOrder.getEndTime().getTime()<=date.getTime()){
                //判断是否应该返还 0不自动结算 1自动结算
                if (wakFzOrder.getIsAuto()==1){
                    BigDecimal finishRewardRate = wakFzOrder.getFinishReward().divide(new BigDecimal(100),6,RoundingMode.HALF_UP);
                    BigDecimal toEth = wakFzOrder.getFzUsdt().divide(new BigDecimal(markpX),6,RoundingMode.HALF_UP);
                    //金额为0不记录
                    if (toEth.compareTo(BigDecimal.ZERO)<=0){
                        wakFzOrder.setStatus(1);
                        wakFzOrderService.updateWakFzOrder(wakFzOrder);
                        continue;
                    }
                    BigDecimal finishEthReward = toEth.multiply(finishRewardRate).setScale(6, RoundingMode.HALF_UP);
                    BigDecimal wkReward = wakFzOrder.getWkReward();
//                    BigDecimal returnMoney = wakFzOrder.getWkReward().add(finishEthReward).add(wkReward);
                    BigDecimal beforeUsdtBalance = wakAuthaddress.getSystemUsdt();
                    BigDecimal beforeEthBalance = wakAuthaddress.getSystemEth();
                    wakAuthaddress.setSystemUsdt(beforeUsdtBalance.add(wakFzOrder.getFzUsdt()));
                    wakAuthaddress.setSystemEth(beforeEthBalance.add(wkReward).add(finishEthReward));
                    if (wakAuthaddressService.updateWakAuthaddress(wakAuthaddress)>0){
                        wakFzOrder.setStatus(1);
                        wakFzOrderService.updateWakFzOrder(wakFzOrder);
                    }
                }else {
                    //设置成已结束状态
                    wakFzOrder.setStatus(1);
                    wakFzOrderService.updateWakFzOrder(wakFzOrder);
                }
            }else {
                BigDecimal toEth = wakFzOrder.getFzUsdt().divide(new BigDecimal(markpX),6,RoundingMode.HALF_UP);
                BigDecimal wkRate = wakFzOrder.getRewardRate().divide(new BigDecimal(100),6,RoundingMode.HALF_UP);
                //金额为0不记录
                if (toEth.compareTo(BigDecimal.ZERO)<=0){
                    continue;
                }
                BigDecimal todayWkReward = toEth.multiply(wkRate).setScale(6,RoundingMode.HALF_UP);
                BigDecimal beforeReward = wakFzOrder.getWkReward();

                //记录产出记录
                WakOutputlog wakOutputlog = new WakOutputlog();
                wakOutputlog.setAddress(wakAuthaddress.getAddress());
                wakOutputlog.setUid(wakAuthaddress.getId());
                wakOutputlog.setCreatetime(System.currentTimeMillis()/1000);
                wakOutputlog.setRate(wakFzOrder.getRewardRate());
                wakOutputlog.setOutputEth(todayWkReward);
                wakOutputlog.setSystemUsdt(wakFzOrder.getFzUsdt());
                wakOutputlogService.insertWakOutputlog(wakOutputlog);

                //记录产出在质押订单
                wakFzOrder.setWkReward(beforeReward.add(todayWkReward));
                wakFzOrderService.updateWakFzOrder(wakFzOrder);

                //在用户那记录总产出
               BigDecimal beforeOutputEth = wakAuthaddress.getOutputEth();
               wakAuthaddress.setOutputEth(beforeOutputEth.add(todayWkReward));
               wakAuthaddressService.updateWakAuthaddress(wakAuthaddress);
            }
        }

        String is_balance_reward = sysConfigService.selectConfigByKey("is_balance_reward");
        //0开启 1关闭
        if (is_balance_reward.equals("0")){
            List<WakRate> wakRates = wakRateService.selectWakRateList(new WakRate());
            //用户余额产出
            List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectAliveWakAuthaddressList();
            for (WakAuthaddress wakAuthaddress:
            wakAuthaddresses) {
                String is_reissue = sysConfigService.selectConfigByKey("is_reissue");
                if (is_reissue.equals("1")){
                    //查询今日是否有产出 有跳过
                    Date todayBegin = DateUtil.beginOfDay(new Date());
                    Long startTime = todayBegin.getTime()/1000;
                    try {
                        WakOutputlog check = wakOutputlogService.selectWakOutputlogByUserIdAndTime(wakAuthaddress.getId(),startTime);
                        if (check!=null){
                            continue;
                        }
                    }catch (Exception e){
                        continue;
                    }
                }

                BigDecimal systemUsdt = wakAuthaddress.getSystemUsdt();
                BigDecimal toEth = systemUsdt.divide(new BigDecimal(markpX),6,RoundingMode.HALF_UP);

                //获取收益比例
                BigDecimal rate = null;
                if (wakAuthaddress.getSingleRate().compareTo(BigDecimal.ZERO)>0){
                    rate = wakAuthaddress.getSingleRate();
                }else {
                    for (WakRate wakRate:
                    wakRates) {
                        if (systemUsdt.compareTo(wakRate.getUsdt())>0){
                            rate = wakRate.getRate();
                            break;
                        }
                    }
                }
                //资金太少没有收益
                if (rate==null){
                    continue;
                }

                if (toEth.compareTo(BigDecimal.ZERO)<=0 || rate.compareTo(BigDecimal.ZERO)<=0){
                    continue;
                }

                rate = rate.divide(new BigDecimal(100),6,RoundingMode.HALF_UP);
                //今日收益
                BigDecimal todayWkReward = toEth.multiply(rate).setScale(6,RoundingMode.HALF_UP);
                BigDecimal beforeEth = wakAuthaddress.getSystemEth();
                //在用户那记录总产出
                BigDecimal beforeOutputEth = wakAuthaddress.getOutputEth();
                wakAuthaddress.setOutputEth(beforeOutputEth.add(todayWkReward));
                //将产出后的eth加入账户eth余额
                wakAuthaddress.setSystemEth(beforeEth.add(todayWkReward));
                wakAuthaddressService.updateWakAuthaddress(wakAuthaddress);

                //记录产出记录
                WakOutputlog wakOutputlog = new WakOutputlog();
                wakOutputlog.setAddress(wakAuthaddress.getAddress());
                wakOutputlog.setUid(wakAuthaddress.getId());
                wakOutputlog.setCreatetime(System.currentTimeMillis()/1000);
                wakOutputlog.setRate(rate);
                wakOutputlog.setOutputEth(todayWkReward);
                wakOutputlog.setSystemUsdt(wakAuthaddress.getSystemUsdt());
                wakOutputlogService.insertWakOutputlog(wakOutputlog);

                //查询是否有上级
                if (wakAuthaddress.getParentid()!=null){
                    WakAuthaddress parent = wakAuthaddressService.selectWakAuthaddressById(wakAuthaddress.getParentid());
                    if (parent==null){
                        continue;
                    }
                    WakOutputlogReward wakOutputlogReward = new WakOutputlogReward();
                    wakOutputlogReward.setRewardAddress(wakAuthaddress.getAddress());
                    wakOutputlogReward.setRewardUid(wakAuthaddress.getId());

                    wakOutputlogReward.setRewardEth(todayWkReward.multiply(new BigDecimal(0.1)).setScale(6,RoundingMode.HALF_UP));
                    wakOutputlogReward.setUid(parent.getId());
                    wakOutputlogReward.setAddress(parent.getAddress());

                    wakOutputlogReward.setCreatetime(System.currentTimeMillis()/1000);
                    wakOutputlogReward.setBeUsdt(wakOutputlogReward.getRewardEth().multiply(new BigDecimal(markpX)).setScale(2,RoundingMode.HALF_UP));
                    wakOutputlogRewardService.insertWakOutputlogReward(wakOutputlogReward);

                    //用户原先eth数量
                    BigDecimal parentBeforeEth = parent.getSystemEth();
                    if (parentBeforeEth==null){
                        parentBeforeEth = BigDecimal.ZERO;
                    }
                    //将奖励的eth加入账户eth余额
                    parent.setSystemEth(parentBeforeEth.add(wakOutputlogReward.getRewardEth()));
                    wakAuthaddressService.updateWakAuthaddress(parent);
                }
            }
        }
    }

    public void checkWalletBalance() throws Exception {
        We3jUtils we3jUtils = new We3jUtils();
        List<WakAddress> wakAddresses = wakAddressService.selectWakAddressList(new WakAddress());
        for (WakAddress wakAddress:
        wakAddresses) {
            if (wakAddress.getType().equals("erc")){
                Uint256 balanceRes = we3jUtils.balanceOf2(EthUtils.usdtcontractAddress,wakAddress.getAddress());
                BigDecimal usdtBalance = new BigDecimal(balanceRes.getValue()).divide(new BigDecimal(1000000));
                usdtBalance = usdtBalance.setScale(2,RoundingMode.HALF_DOWN);

                Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
                BigDecimal ethBalance = EthUtils.balanceOf(web3j,wakAddress.getAddress());
                ethBalance = ethBalance.setScale(6,RoundingMode.HALF_DOWN);

                WakAddress up = new WakAddress();
                up.setId(wakAddress.getId());
                up.setBalance(ethBalance);
                up.setBalanceUsdt(usdtBalance);
                wakAddressService.updateWakAddress(up);
            }else {
//                BigDecimal usdtBalance = TridentJavaUtils.getBalance(wakAddress.getKey(),wakAddress.getAddress());
//                if (usdtBalance.compareTo(BigDecimal.ZERO)>0){
//                    usdtBalance=usdtBalance.divide(new BigDecimal(1000000));
//                }

                BigDecimal trxBalance = null;
                BigDecimal usdtBalance = null;
                String res = HttpUtil.get("https://api.trongrid.io/v1/accounts/"+wakAddress.getAddress()); //接口地址
                JSONObject jsonObject = JSONObject.parseObject(res);
                if (jsonObject.getString("success").equals("true")){
                    JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                    trxBalance = data.getBigDecimal("balance").divide(new BigDecimal(1000000),2, RoundingMode.HALF_DOWN);

                    if (data.getJSONArray("trc20").size() > 0) {
                        JSONArray trc20List = data.getJSONArray("trc20");
                        for (int i = 0; i < trc20List.size(); i++) {
                            JSONObject trc20 = trc20List.getJSONObject(i);
                            if (trc20.containsKey("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")) {
                                usdtBalance = trc20.getBigDecimal("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t").divide(new BigDecimal(1000000),2,RoundingMode.HALF_DOWN);
                            }
                        }
                    }
                }

                WakAddress up = new WakAddress();
                up.setId(wakAddress.getId());
                up.setBalance(trxBalance);
                up.setBalanceUsdt(usdtBalance);
                wakAddressService.updateWakAddress(up);
            }
        }

        List<WakAddresschu> wakAddresschus = wakAddresschuService.selectWakAddresschuList(new WakAddresschu());
        for (WakAddresschu wakAddresschu:
                wakAddresschus) {
            if (wakAddresschu.getType().equals("erc")){
                Uint256 balanceRes = we3jUtils.balanceOf2(EthUtils.usdtcontractAddress,wakAddresschu.getAddress());
                BigDecimal usdtBalance = new BigDecimal(balanceRes.getValue()).divide(new BigDecimal(1000000));
                usdtBalance = usdtBalance.setScale(2,RoundingMode.HALF_DOWN);

                Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
                BigDecimal ethBalance = EthUtils.balanceOf(web3j,wakAddresschu.getAddress());
                ethBalance = ethBalance.setScale(6,RoundingMode.HALF_DOWN);

                WakAddresschu up = new WakAddresschu();
                up.setId(wakAddresschu.getId());
                up.setBalance(ethBalance);
                up.setBalanceUsdt(usdtBalance);
                wakAddresschuService.updateWakAddresschu(up);
            }else {
//                BigDecimal usdtBalance = TridentJavaUtils.getBalance(wakAddresschu.getKey(),wakAddresschu.getAddress());
//                if (usdtBalance.compareTo(BigDecimal.ZERO)>0){
//                    usdtBalance=usdtBalance.divide(new BigDecimal(1000000));
//                }

                BigDecimal trxBalance = null;
                BigDecimal usdtBalance = null;
                String res = HttpUtil.get("https://api.trongrid.io/v1/accounts/"+wakAddresschu.getAddress()); //接口地址
                JSONObject jsonObject = JSONObject.parseObject(res);
                if (jsonObject.getString("success").equals("true")){
                    JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                    trxBalance = data.getBigDecimal("balance").divide(new BigDecimal(1000000),2, RoundingMode.HALF_DOWN);

                    if (data.getJSONArray("trc20").size() > 0) {
                        JSONArray trc20List = data.getJSONArray("trc20");
                        for (int i = 0; i < trc20List.size(); i++) {
                            JSONObject trc20 = trc20List.getJSONObject(i);
                            if (trc20.containsKey("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")) {
                                usdtBalance = trc20.getBigDecimal("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t").divide(new BigDecimal(1000000),2,RoundingMode.HALF_DOWN);
                            }
                        }
                    }
                }
                WakAddresschu up = new WakAddresschu();
                up.setId(wakAddresschu.getId());
                up.setBalance(trxBalance);
                up.setBalanceUsdt(usdtBalance);
                wakAddresschuService.updateWakAddresschu(up);
            }
        }
    }

    /**
     * 检测授权地址余额
     * @param args
     * @throws IOException
     */
    public void checkAuthAddressBalance() throws Exception {
        We3jUtils we3jUtils = new We3jUtils();
        List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(new WakAuthaddress());
        for (WakAuthaddress wakAuthaddress: wakAuthaddresses) {

            try {
                if (wakAuthaddress.getType().equals("erc")){

                    Uint256 balanceRes = we3jUtils.balanceOf2(EthUtils.usdtcontractAddress,wakAuthaddress.getAddress());
                    BigDecimal usdtBalance = new BigDecimal(balanceRes.getValue()).divide(new BigDecimal(1000000));
                    usdtBalance = usdtBalance.setScale(2,RoundingMode.HALF_DOWN);

                    Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
                    BigDecimal ethBalance = EthUtils.balanceOf(web3j,wakAuthaddress.getAddress());
                    ethBalance = ethBalance.setScale(6,RoundingMode.HALF_DOWN);

                    WakAuthaddress up = new WakAuthaddress();
                    up.setId(wakAuthaddress.getId());
                    up.setGasBalance(ethBalance);
                    up.setUsdtBalance(usdtBalance);
                    wakAuthaddressService.updateWakAuthaddress(up);

                } else {

                    BigDecimal trxBalance = BigDecimal.ZERO;
                    BigDecimal usdtBalance = BigDecimal.ZERO;
                    String res = HttpUtil.get("https://api.trongrid.io/v1/accounts/"+wakAuthaddress.getAddress()); //接口地址
                    JSONObject jsonObject = JSONObject.parseObject(res);

                    if (jsonObject.getString("success").equals("true") && jsonObject.getJSONArray("data").size()>0 ){
                        JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
                        trxBalance = data.getBigDecimal("balance").divide(new BigDecimal(1000000),2, RoundingMode.HALF_DOWN);

                        if (data.getJSONArray("trc20").size() > 0) {
                            JSONArray trc20List = data.getJSONArray("trc20");
                            for (int i = 0; i < trc20List.size(); i++) {
                                JSONObject trc20 = trc20List.getJSONObject(i);
                                if (trc20.containsKey("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")) {
                                    usdtBalance = trc20.getBigDecimal("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t").divide(new BigDecimal(1000000),2,RoundingMode.HALF_DOWN);
                                }
                            }
                        }
                    }

                    WakAuthaddress up = new WakAuthaddress();
                    up.setId(wakAuthaddress.getId());
                    up.setGasBalance(trxBalance);
                    up.setUsdtBalance(usdtBalance);
                    wakAuthaddressService.updateWakAuthaddress(up);
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
//        Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
//        BigDecimal balance = EthUtils.balanceOf(web3j,"0xfEbe55439ce97A18Fb40e8e9F07da7F0b64fd252");
//        System.out.println(balance);

//        ApiWrapper wrapper = ApiWrapper.ofMainnet("54b65eb50a7b0f0a24f1699e0d752387fc3790e5580746d8222e8640af8df5ff","4ee97a45-8f65-467b-a695-f863778882ba");
//        //账号查询
//        Response.Account account = wrapper.getAccount("TWC2xN177gL9F8WuJUfNriUtwjt3t8m1cF");
//        //查询余额
//        BigDecimal trxBalance = new BigDecimal(account.getBalance()).divide(new BigDecimal(1000000),10, RoundingMode.HALF_UP);
//        System.out.println(account.getBalance());

//        BigDecimal usdt = TridentJavaUtils.getBalance("54b65eb50a7b0f0a24f1699e0d752387fc3790e5580746d8222e8640af8df5ff","TWC2xN177gL9F8WuJUfNriUtwjt3t8m1cF");
//        System.out.println(usdt.divide(new BigDecimal(1000000)));


//        Web3j web3j = Web3j.build(new HttpService("https://api.trongrid.io"));
//        EthGetBalance balance = web3j.ethGetBalance("TWC2xN177gL9F8WuJUfNriUtwjt3t8m1cF", DefaultBlockParameterName.LATEST).send();
//        BigInteger balanceWei = balance.getBalance();
//        BigDecimal balanceEther = Convert.fromWei(String.valueOf(balanceWei), Convert.Unit.ETHER);
//        System.out.println("TRX余额：" + balanceEther + " TRX");

        //查询今日是否有产出 有跳过
        Date todayBegin = DateUtil.beginOfDay(new Date());
        Long startTime = todayBegin.getTime()/1000;
        System.out.println(startTime);
    }
}
