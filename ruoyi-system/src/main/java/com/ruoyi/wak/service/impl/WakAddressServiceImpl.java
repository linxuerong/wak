package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakAddressMapper;
import com.ruoyi.wak.domain.WakAddress;
import com.ruoyi.wak.service.IWakAddressService;

/**
 * 控制地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakAddressServiceImpl implements IWakAddressService 
{
    @Autowired
    private WakAddressMapper wakAddressMapper;

    /**
     * 查询控制地址
     * 
     * @param id 控制地址主键
     * @return 控制地址
     */
    @Override
    public WakAddress selectWakAddressById(Long id)
    {
        return wakAddressMapper.selectWakAddressById(id);
    }

    /**
     * 查询控制地址列表
     * 
     * @param wakAddress 控制地址
     * @return 控制地址
     */
    @Override
    public List<WakAddress> selectWakAddressList(WakAddress wakAddress)
    {
        return wakAddressMapper.selectWakAddressList(wakAddress);
    }

    /**
     * 新增控制地址
     * 
     * @param wakAddress 控制地址
     * @return 结果
     */
    @Override
    public int insertWakAddress(WakAddress wakAddress)
    {
        return wakAddressMapper.insertWakAddress(wakAddress);
    }

    /**
     * 修改控制地址
     * 
     * @param wakAddress 控制地址
     * @return 结果
     */
    @Override
    public int updateWakAddress(WakAddress wakAddress)
    {
        return wakAddressMapper.updateWakAddress(wakAddress);
    }

    /**
     * 批量删除控制地址
     * 
     * @param ids 需要删除的控制地址主键
     * @return 结果
     */
    @Override
    public int deleteWakAddressByIds(Long[] ids)
    {
        return wakAddressMapper.deleteWakAddressByIds(ids);
    }

    /**
     * 删除控制地址信息
     * 
     * @param id 控制地址主键
     * @return 结果
     */
    @Override
    public int deleteWakAddressById(Long id)
    {
        return wakAddressMapper.deleteWakAddressById(id);
    }
}
