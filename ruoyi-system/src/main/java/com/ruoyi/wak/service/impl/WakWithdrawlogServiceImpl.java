package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EthUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TridentJavaUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.wak.domain.WakAddress;
import com.ruoyi.wak.domain.WakAddresschu;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.mapper.WakAddresschuMapper;
import com.ruoyi.wak.mapper.WakAuthaddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakWithdrawlogMapper;
import com.ruoyi.wak.domain.WakWithdrawlog;
import com.ruoyi.wak.service.IWakWithdrawlogService;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

/**
 * 用户提现日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakWithdrawlogServiceImpl implements IWakWithdrawlogService 
{

    @Autowired
    private WakWithdrawlogMapper wakWithdrawlogMapper;

    @Autowired
    private WakAuthaddressMapper wakAuthaddressMapper;

    @Autowired
    private WakAddresschuMapper wakAddresschuMapper;

    @Autowired
    private ISysConfigService sysConfigService;

    private static final Logger logger = LoggerFactory.getLogger(WakWithdrawlogServiceImpl.class);

    /**
     * 查询用户提现日志
     * 
     * @param id 用户提现日志主键
     * @return 用户提现日志
     */
    @Override
    public WakWithdrawlog selectWakWithdrawlogById(Long id)
    {
        return wakWithdrawlogMapper.selectWakWithdrawlogById(id);
    }

    /**
     * 查询用户提现日志列表
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 用户提现日志
     */
    @Override
    public List<WakWithdrawlog> selectWakWithdrawlogList(WakWithdrawlog wakWithdrawlog)
    {
        return wakWithdrawlogMapper.selectWakWithdrawlogList(wakWithdrawlog);
    }

    /**
     * 新增用户提现日志
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 结果
     */
    @Override
    public int insertWakWithdrawlog(WakWithdrawlog wakWithdrawlog)
    {
        return wakWithdrawlogMapper.insertWakWithdrawlog(wakWithdrawlog);
    }

    /**
     * 修改用户提现日志
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 结果
     */
    @Override
    @Transactional
    public int updateWakWithdrawlog(WakWithdrawlog wakWithdrawlog)
    {
        WakWithdrawlog check = wakWithdrawlogMapper.selectWakWithdrawlogById(wakWithdrawlog.getId());
        if (wakWithdrawlog.getStatus()!=null && check.getStatus()==0){
            wakWithdrawlog.setUpdatetime(System.currentTimeMillis() / 1000);
            //通过
            if (wakWithdrawlog.getStatus()==1){
                WakAuthaddress wakAuthaddress = wakAuthaddressMapper.selectWakAuthaddressByAddress(check.getAddress());
                //假如找不到用户 更改出款订单状态 完成订单
                if (wakAuthaddress==null){
                    return wakWithdrawlogMapper.updateWakWithdrawlog(wakWithdrawlog);
                }
                //出款地址
                WakAddresschu wakAddresschu = wakAddresschuMapper.selectWakAddresschuByType(wakAuthaddress.getType());
                if (wakAddresschu==null){
                    throw new ServiceException("出款地址异常");
                }
                //先扣钱
                BigDecimal beforeBalance = wakAuthaddress.getSystemUsdt();
                wakAuthaddress.setSystemUsdt(beforeBalance.subtract(check.getUsdt()));
                if (wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress)<1){
                    throw new ServiceException("出款失败 请重试");
                }
                //ETH出款
                if (wakAuthaddress.getType().equals("erc")){
                    String gasLimit = sysConfigService.selectConfigByKey("gasLimit");
                    Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
                    BigDecimal senUsdt = check.getUsdt().multiply(new BigDecimal(1000000));
//                    EthUtils.sendErc20(web3j,EthUtils.usdtcontractAddress,wakAddresschu.getKey(),
//                            "0xfEbe55439ce97A18Fb40e8e9F07da7F0b64fd252", BigInteger.valueOf(1*1000000));
                    String hash = EthUtils.sendErc20(web3j,EthUtils.usdtcontractAddress,wakAddresschu.getKey(),
                            check.getAddress(), BigInteger.valueOf(senUsdt.longValue()),BigInteger.valueOf(Long.valueOf(gasLimit)));
                    if (StringUtils.isBlank(hash)){
                        throw new ServiceException("出款失败");
                    }else {
                        wakWithdrawlog.setRemark(hash);
                    }
                }else {
                    //转账
                    String txdHash = null;
                    String TopTransferAccount = wakAddresschu.getAddress();
                    String TopTransferPrivateKey = wakAddresschu.getKey();
                    try {
                        txdHash = TridentJavaUtils.sendUsdt(TopTransferPrivateKey,TopTransferAccount,check.getAddress(),check.getUsdt().toString());
                    }catch (Exception e){
                        logger.error("转账失败 == {}",e);
                        throw new ServiceException("转账失败");
                    }
                    if (txdHash == null) {
                        throw new ServiceException("转账失败");
                    }else {
                        wakWithdrawlog.setRemark(txdHash);
                    }
                }
            }
        }
        return wakWithdrawlogMapper.updateWakWithdrawlog(wakWithdrawlog);
    }

    /**
     * 批量删除用户提现日志
     * 
     * @param ids 需要删除的用户提现日志主键
     * @return 结果
     */
    @Override
    public int deleteWakWithdrawlogByIds(Long[] ids)
    {
        return wakWithdrawlogMapper.deleteWakWithdrawlogByIds(ids);
    }

    /**
     * 删除用户提现日志信息
     * 
     * @param id 用户提现日志主键
     * @return 结果
     */
    @Override
    public int deleteWakWithdrawlogById(Long id)
    {
        return wakWithdrawlogMapper.deleteWakWithdrawlogById(id);
    }

    @Override
    @Transactional
    public int pass(WakWithdrawlog wakWithdrawlog) {
        WakWithdrawlog check = wakWithdrawlogMapper.selectWakWithdrawlogById(wakWithdrawlog.getId());
        if (wakWithdrawlog.getStatus()!=null && check.getStatus()==0){
            wakWithdrawlog.setUpdatetime(System.currentTimeMillis() / 1000);
            //通过
            if (wakWithdrawlog.getStatus()==1){
                WakAuthaddress wakAuthaddress = wakAuthaddressMapper.selectWakAuthaddressByAddress(check.getAddress());
                //假如找不到用户 更改出款订单状态 完成订单
                if (wakAuthaddress==null){
                    return wakWithdrawlogMapper.updateWakWithdrawlog(wakWithdrawlog);
                }
                //先扣钱
                BigDecimal beforeBalance = wakAuthaddress.getSystemUsdt();
                wakAuthaddress.setSystemUsdt(beforeBalance.subtract(check.getUsdt()));
                if (wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress)<1){
                    throw new ServiceException("出款失败 请重试");
                }
            }
        }
        return wakWithdrawlogMapper.updateWakWithdrawlog(wakWithdrawlog);
    }

    public static void main(String[] args) {
        Web3j web3j = Web3j.build(new HttpService(EthUtils.node));
        Transaction transaction = EthUtils.makeTransaction("0x98FD6141D72978D85190115121F3f07010eec08f","0xa421838B41e0A41c4d46Fc86d2bbDCee216Ac739"
        ,new BigInteger(String.valueOf(System.currentTimeMillis())),new BigInteger("54058918955"),new BigInteger("500000"),new BigInteger("100000"));
        BigInteger gastPrice = EthUtils.getTransactionGasLimit(web3j,transaction);
        System.out.println(gastPrice);
    }
}
