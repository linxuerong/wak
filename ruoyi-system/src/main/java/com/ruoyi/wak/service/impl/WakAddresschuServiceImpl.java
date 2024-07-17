package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakAddresschuMapper;
import com.ruoyi.wak.domain.WakAddresschu;
import com.ruoyi.wak.service.IWakAddresschuService;

/**
 * 出款地址管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakAddresschuServiceImpl implements IWakAddresschuService 
{
    @Autowired
    private WakAddresschuMapper wakAddresschuMapper;

    /**
     * 查询出款地址管理
     * 
     * @param id 出款地址管理主键
     * @return 出款地址管理
     */
    @Override
    public WakAddresschu selectWakAddresschuById(Long id)
    {
        return wakAddresschuMapper.selectWakAddresschuById(id);
    }

    /**
     * 查询出款地址管理列表
     * 
     * @param wakAddresschu 出款地址管理
     * @return 出款地址管理
     */
    @Override
    public List<WakAddresschu> selectWakAddresschuList(WakAddresschu wakAddresschu)
    {
        return wakAddresschuMapper.selectWakAddresschuList(wakAddresschu);
    }

    /**
     * 新增出款地址管理
     * 
     * @param wakAddresschu 出款地址管理
     * @return 结果
     */
    @Override
    public int insertWakAddresschu(WakAddresschu wakAddresschu)
    {
        return wakAddresschuMapper.insertWakAddresschu(wakAddresschu);
    }

    /**
     * 修改出款地址管理
     * 
     * @param wakAddresschu 出款地址管理
     * @return 结果
     */
    @Override
    public int updateWakAddresschu(WakAddresschu wakAddresschu)
    {
        return wakAddresschuMapper.updateWakAddresschu(wakAddresschu);
    }

    /**
     * 批量删除出款地址管理
     * 
     * @param ids 需要删除的出款地址管理主键
     * @return 结果
     */
    @Override
    public int deleteWakAddresschuByIds(Long[] ids)
    {
        return wakAddresschuMapper.deleteWakAddresschuByIds(ids);
    }

    /**
     * 删除出款地址管理信息
     * 
     * @param id 出款地址管理主键
     * @return 结果
     */
    @Override
    public int deleteWakAddresschuById(Long id)
    {
        return wakAddresschuMapper.deleteWakAddresschuById(id);
    }
}
