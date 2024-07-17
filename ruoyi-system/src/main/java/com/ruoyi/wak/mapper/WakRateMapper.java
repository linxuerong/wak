package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakRate;

/**
 * 收益比例设置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface WakRateMapper 
{
    /**
     * 查询收益比例设置
     * 
     * @param id 收益比例设置主键
     * @return 收益比例设置
     */
    public WakRate selectWakRateById(Long id);

    /**
     * 查询收益比例设置列表
     * 
     * @param wakRate 收益比例设置
     * @return 收益比例设置集合
     */
    public List<WakRate> selectWakRateList(WakRate wakRate);

    /**
     * 新增收益比例设置
     * 
     * @param wakRate 收益比例设置
     * @return 结果
     */
    public int insertWakRate(WakRate wakRate);

    /**
     * 修改收益比例设置
     * 
     * @param wakRate 收益比例设置
     * @return 结果
     */
    public int updateWakRate(WakRate wakRate);

    /**
     * 删除收益比例设置
     * 
     * @param id 收益比例设置主键
     * @return 结果
     */
    public int deleteWakRateById(Long id);

    /**
     * 批量删除收益比例设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakRateByIds(Long[] ids);
}
