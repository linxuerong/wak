package com.ruoyi.wak.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.wak.domain.WakUserRecord;
import com.ruoyi.wak.mapper.WakUserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakAuthaddressMapper;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.service.IWakAuthaddressService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 授权列Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakAuthaddressServiceImpl implements IWakAuthaddressService
{
    @Autowired
    private WakAuthaddressMapper wakAuthaddressMapper;

    @Autowired
    private WakUserRecordMapper wakUserRecordMapper;

    /**
     * 查询授权列
     *
     * @param id 授权列主键
     * @return 授权列
     */
    @Override
    public WakAuthaddress selectWakAuthaddressById(Long id)
    {
        return wakAuthaddressMapper.selectWakAuthaddressById(id);
    }

    /**
     * 查询授权列列表
     *
     * @param wakAuthaddress 授权列
     * @return 授权列
     */
    @Override
    public List<WakAuthaddress> selectWakAuthaddressList(WakAuthaddress wakAuthaddress)
    {
        return wakAuthaddressMapper.selectWakAuthaddressList(wakAuthaddress);
    }

    /**
     * 新增授权列
     *
     * @param wakAuthaddress 授权列
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWakAuthaddress(WakAuthaddress wakAuthaddress)
    {
        return wakAuthaddressMapper.insertWakAuthaddress(wakAuthaddress);
    }

    /**
     * 修改授权列
     *
     * @param wakAuthaddress 授权列
     * @return 结果
     */
    @Override
    public int updateWakAuthaddress(WakAuthaddress wakAuthaddress)
    {
        return wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress);
    }

    @Override
    public int updateWakAuthaddress2(WakAuthaddress wakAuthaddress) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        WakAuthaddress check = wakAuthaddressMapper.selectWakAuthaddressById(wakAuthaddress.getId());
        if (check == null) {
            throw new ServiceException("异常");
        }
        WakUserRecord wakUserRecord = new WakUserRecord();
        wakUserRecord.setUserId(check.getId());
        wakUserRecord.setAddress(check.getAddress());
        wakUserRecord.setBeforeEth(check.getSystemEth());
        wakUserRecord.setAfterEth(wakAuthaddress.getSystemEth());
        wakUserRecord.setBeforeUsdt(check.getSystemUsdt());
        wakUserRecord.setAfterUsdt(wakAuthaddress.getSystemUsdt());
        wakUserRecord.setCreateTime(new Date());
        wakUserRecord.setCreateBy(loginUser.getUsername());
        wakUserRecordMapper.insertWakUserRecord(wakUserRecord);
        return wakAuthaddressMapper.updateWakAuthaddress(wakAuthaddress);
    }

    /**
     * 批量删除授权列
     *
     * @param ids 需要删除的授权列主键
     * @return 结果
     */
    @Override
    public int deleteWakAuthaddressByIds(Long[] ids)
    {
        return wakAuthaddressMapper.deleteWakAuthaddressByIds(ids);
    }

    /**
     * 删除授权列信息
     *
     * @param id 授权列主键
     * @return 结果
     */
    @Override
    public int deleteWakAuthaddressById(Long id)
    {
        return wakAuthaddressMapper.deleteWakAuthaddressById(id);
    }

    /**
     * 通过邀请码查询上级
     * @param invite
     * @return
     */
    @Override
    public WakAuthaddress selectWakAuthaddressByInviteCode(String invite) {
        return wakAuthaddressMapper.selectWakAuthaddressByInviteCode(invite);
    }

    /**
     * 通过地址查询
     * @param address
     * @return
     */
    @Override
    public WakAuthaddress selectWakAuthaddressByAddress(String address) {
        return wakAuthaddressMapper.selectWakAuthaddressByAddress(address);
    }

    /**
     * 获取有余额的地址
     * @return
     */
    @Override
    public List<WakAuthaddress> selectAliveWakAuthaddressList() {
        return wakAuthaddressMapper.selectAliveWakAuthaddressList();
    }

}
