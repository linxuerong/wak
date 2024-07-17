package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakConfig;

/**
 * 平台配置Service接口
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public interface IWakConfigService 
{
    /**
     * 查询平台配置
     * 
     * @param id 平台配置主键
     * @return 平台配置
     */
    public WakConfig selectWakConfigById(Integer id);

    /**
     * 查询平台配置列表
     * 
     * @param wakConfig 平台配置
     * @return 平台配置集合
     */
    public List<WakConfig> selectWakConfigList(WakConfig wakConfig);

    /**
     * 新增平台配置
     * 
     * @param wakConfig 平台配置
     * @return 结果
     */
    public int insertWakConfig(WakConfig wakConfig);

    /**
     * 修改平台配置
     * 
     * @param wakConfig 平台配置
     * @return 结果
     */
    public int updateWakConfig(WakConfig wakConfig);

    /**
     * 批量删除平台配置
     * 
     * @param ids 需要删除的平台配置主键集合
     * @return 结果
     */
    public int deleteWakConfigByIds(Integer[] ids);

    /**
     * 删除平台配置信息
     * 
     * @param id 平台配置主键
     * @return 结果
     */
    public int deleteWakConfigById(Integer id);

    /**
     * 通过键获取值
     * @param name
     * @return
     */
    WakConfig selectWakConfigByName(String name);

    /**
     * 通过键获取值
     * @param searchName
     * @return
     */
    String selectWakAuthaddressByName(String searchName);
}
