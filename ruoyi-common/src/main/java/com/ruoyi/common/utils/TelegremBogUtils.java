package com.ruoyi.common.utils;

import com.ruoyi.common.utils.http.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TelegremBogUtils {


    public static void tongzhi(String botkey,String chatId,String msg)
    {
        HttpUtils.sendGet("https://api.telegram.org/bot"+botkey+"/sendMessage?chat_id=" + chatId + "&text="+msg);
    }

    public static void tongzhiSuper(String botkey,String chatId,String msg)
    {
        HttpUtils.sendGet("https://api.telegram.org/bot"+botkey+"/sendMessage?migrate_to_chat_id=" + chatId + "&text="+msg);
    }

    public static void main(String[] args) {
//        String msg = "用户:[" + "385" + "] 昵称[" + "北越组 - 顺风（老7576）" +
//                "]余额变更，"+"%0A"+
//                "地址：" + "0x6eE84D7CFe361C415B1547495a8B3786123cD306" + "%0A" +
//                "ETH历史:" + "0.000542" + "%0A" +
//                "ETH变动:" + "0.000542" + "%0A" +
//                "ETH最新:" + "0.000000" + "%0A" +
//                "%0A" +
//                "USDT历史:" + "95.35" + "%0A" +
//                "USDT变动:" + "1.24" + "%0A" +
//                "USDT最新:" + "96.59";
        String msg = "test";
//                    "USDT当前余额变更:" + newUsdt;
        String alarm_bot =  "6885606291:AAGVZ6gy3lDRkobPjZMBCGmoWAeMGA4AJ5k";
        String alarm_chatid = "-1002006874395";
        try {
            TelegremBogUtils.tongzhi(alarm_bot, alarm_chatid, msg);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
