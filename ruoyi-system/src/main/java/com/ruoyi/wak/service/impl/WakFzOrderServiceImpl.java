package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.mapper.WakAuthaddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakFzOrderMapper;
import com.ruoyi.wak.domain.WakFzOrder;
import com.ruoyi.wak.service.IWakFzOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 质押订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
@Service
public class WakFzOrderServiceImpl implements IWakFzOrderService 
{
    @Autowired
    private WakFzOrderMapper wakFzOrderMapper;

    @Autowired
    private WakAuthaddressMapper wakAuthaddressMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询质押订单
     * 
     * @param id 质押订单主键
     * @return 质押订单
     */
    @Override
    public WakFzOrder selectWakFzOrderById(Long id)
    {
        return wakFzOrderMapper.selectWakFzOrderById(id);
    }

    /**
     * 查询质押订单列表
     * 
     * @param wakFzOrder 质押订单
     * @return 质押订单
     */
    @Override
    public List<WakFzOrder> selectWakFzOrderList(WakFzOrder wakFzOrder)
    {
        return wakFzOrderMapper.selectWakFzOrderList(wakFzOrder);
    }

    /**
     * 新增质押订单
     * 
     * @param wakFzOrder 质押订单
     * @return 结果
     */
    @Override
    @Transactional
    public int insertWakFzOrder(WakFzOrder wakFzOrder)
    {
        wakFzOrder.setCreateTime(DateUtils.getNowDate());
        wakFzOrder.setStartTime(wakFzOrder.getCreateTime());
        wakFzOrder.setEndTime(DateUtils.addDays(wakFzOrder.getStartTime(),wakFzOrder.getFzDay()));
        WakAuthaddress wakAuthaddress = wakAuthaddressMapper.selectWakAuthaddressById(wakFzOrder.getUid());
        BigDecimal beforeBalance = wakAuthaddress.getSystemUsdt();
        wakAuthaddress.setSystemUsdt(beforeBalance.subtract(wakFzOrder.getFzUsdt()));
        wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress);
        return wakFzOrderMapper.insertWakFzOrder(wakFzOrder);
    }

    /**
     * 修改质押订单
     * 
     * @param wakFzOrder 质押订单
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWakFzOrder(WakFzOrder wakFzOrder)
    {
        WakFzOrder check = wakFzOrderMapper.selectWakFzOrderById(wakFzOrder.getId());
        if (check==null){
            throw new ServiceException("找不到此质押订单");
        }
        if (check.getStatus()==2){
            if (wakFzOrder.getStatus()!=2){
                throw new ServiceException("该质押单已结束不能修改状态");
            }
        }

        //假如是结束用户质押状态 结算
        if (wakFzOrder.getStatus()==2){
            if (check.getStatus()!=2){
                //查用户
                WakAuthaddress wakAuthaddress = wakAuthaddressMapper.selectWakAuthaddressById(wakFzOrder.getUid());
                if (wakAuthaddress==null){
                    wakFzOrder.setStatus(1);
                    wakFzOrder.setCreateTime(null);
                    return wakFzOrderMapper.updateWakFzOrder(wakFzOrder);
                }
                //eth价格
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

                BigDecimal finishRewardRate = wakFzOrder.getFinishReward().divide(new BigDecimal(100),6, RoundingMode.HALF_UP);
                BigDecimal toEth = wakFzOrder.getFzUsdt().divide(new BigDecimal(markpX),6,RoundingMode.HALF_UP);
                //金额为0不记录
                if (toEth.compareTo(BigDecimal.ZERO)<=0){
                    wakFzOrder.setStatus(1);
                    wakFzOrder.setCreateTime(null);
                    return wakFzOrderMapper.updateWakFzOrder(wakFzOrder);
                }
                BigDecimal finishEthReward = toEth.multiply(finishRewardRate).setScale(6, RoundingMode.HALF_UP);
                BigDecimal wkReward = wakFzOrder.getWkReward();
//                    BigDecimal returnMoney = wakFzOrder.getWkReward().add(finishEthReward).add(wkReward);
                BigDecimal beforeUsdtBalance = wakAuthaddress.getSystemUsdt();
                BigDecimal beforeEthBalance = wakAuthaddress.getSystemEth();
                wakAuthaddress.setSystemUsdt(beforeUsdtBalance.add(wakFzOrder.getFzUsdt()));
                wakAuthaddress.setSystemEth(beforeEthBalance.add(wkReward).add(finishEthReward));
                if (wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress)>0){
                    wakFzOrder.setStatus(2);
                    wakFzOrder.setCreateTime(null);
                    wakFzOrderMapper.updateWakFzOrder(wakFzOrder);
                }
                return 1;
            }
        }

        //验证质押要求是否大于质押usdt
        if (wakFzOrder.getFzUsdtLimit().compareTo(wakFzOrder.getFzUsdt())>0){
            throw new ServiceException("输入的质押usdt，未达到质押要求");
        }

        //查找用户
        WakAuthaddress wakAuthaddress = wakAuthaddressMapper.selectWakAuthaddressById(wakFzOrder.getUid());
        if (wakAuthaddress==null){
            throw new ServiceException("用户不存在");
        }
        wakFzOrder.setAddress(wakAuthaddress.getAddress());
        //验证用户余额是否足够
        if (wakFzOrder.getFzUsdt().compareTo(wakAuthaddress.getSystemUsdt().add(check.getFzUsdt()))>0){
            throw new ServiceException("用户余额不足");
        }

        BigDecimal beforeBalance = wakAuthaddress.getSystemUsdt();
        BigDecimal changeFzUsdt = wakFzOrder.getFzUsdt().subtract(check.getFzUsdt());
        wakAuthaddress.setSystemUsdt(beforeBalance.subtract(changeFzUsdt));
        wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress);

//        wakFzOrder.se(DateUtils.getNowDate());
        wakFzOrder.setCreateTime(null);
        wakFzOrder.setStartTime(check.getCreateTime());
        wakFzOrder.setEndTime(DateUtils.addDays(wakFzOrder.getStartTime(),wakFzOrder.getFzDay()));

        return wakFzOrderMapper.updateWakFzOrder(wakFzOrder);
    }

    /**
     * 批量删除质押订单
     * 
     * @param ids 需要删除的质押订单主键
     * @return 结果
     */
    @Override
    public int deleteWakFzOrderByIds(Long[] ids)
    {
        return wakFzOrderMapper.deleteWakFzOrderByIds(ids);
    }

    /**
     * 删除质押订单信息
     * 
     * @param id 质押订单主键
     * @return 结果
     */
    @Override
    public int deleteWakFzOrderById(Long id)
    {
        return wakFzOrderMapper.deleteWakFzOrderById(id);
    }
}
