package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakRateMapper;
import com.ruoyi.wak.domain.WakRate;
import com.ruoyi.wak.service.IWakRateService;

/**
 * 收益比例设置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class WakRateServiceImpl implements IWakRateService 
{
    @Autowired
    private WakRateMapper wakRateMapper;

    /**
     * 查询收益比例设置
     * 
     * @param id 收益比例设置主键
     * @return 收益比例设置
     */
    @Override
    public WakRate selectWakRateById(Long id)
    {
        return wakRateMapper.selectWakRateById(id);
    }

    /**
     * 查询收益比例设置列表
     * 
     * @param wakRate 收益比例设置
     * @return 收益比例设置
     */
    @Override
    public List<WakRate> selectWakRateList(WakRate wakRate)
    {
        return wakRateMapper.selectWakRateList(wakRate);
    }

    /**
     * 新增收益比例设置
     * 
     * @param wakRate 收益比例设置
     * @return 结果
     */
    @Override
    public int insertWakRate(WakRate wakRate)
    {
        return wakRateMapper.insertWakRate(wakRate);
    }

    /**
     * 修改收益比例设置
     * 
     * @param wakRate 收益比例设置
     * @return 结果
     */
    @Override
    public int updateWakRate(WakRate wakRate)
    {
        return wakRateMapper.updateWakRate(wakRate);
    }

    /**
     * 批量删除收益比例设置
     * 
     * @param ids 需要删除的收益比例设置主键
     * @return 结果
     */
    @Override
    public int deleteWakRateByIds(Long[] ids)
    {
        return wakRateMapper.deleteWakRateByIds(ids);
    }

    /**
     * 删除收益比例设置信息
     * 
     * @param id 收益比例设置主键
     * @return 结果
     */
    @Override
    public int deleteWakRateById(Long id)
    {
        return wakRateMapper.deleteWakRateById(id);
    }
}
