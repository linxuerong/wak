package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakConfigMapper;
import com.ruoyi.wak.domain.WakConfig;
import com.ruoyi.wak.service.IWakConfigService;

/**
 * 平台配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@Service
public class WakConfigServiceImpl implements IWakConfigService 
{
    @Autowired
    private WakConfigMapper wakConfigMapper;

    /**
     * 查询平台配置
     * 
     * @param id 平台配置主键
     * @return 平台配置
     */
    @Override
    public WakConfig selectWakConfigById(Integer id)
    {
        return wakConfigMapper.selectWakConfigById(id);
    }

    /**
     * 查询平台配置列表
     * 
     * @param wakConfig 平台配置
     * @return 平台配置
     */
    @Override
    public List<WakConfig> selectWakConfigList(WakConfig wakConfig)
    {
        return wakConfigMapper.selectWakConfigList(wakConfig);
    }

    /**
     * 新增平台配置
     * 
     * @param wakConfig 平台配置
     * @return 结果
     */
    @Override
    public int insertWakConfig(WakConfig wakConfig)
    {
        return wakConfigMapper.insertWakConfig(wakConfig);
    }

    /**
     * 修改平台配置
     * 
     * @param wakConfig 平台配置
     * @return 结果
     */
    @Override
    public int updateWakConfig(WakConfig wakConfig)
    {
        return wakConfigMapper.updateWakConfig(wakConfig);
    }

    /**
     * 批量删除平台配置
     * 
     * @param ids 需要删除的平台配置主键
     * @return 结果
     */
    @Override
    public int deleteWakConfigByIds(Integer[] ids)
    {
        return wakConfigMapper.deleteWakConfigByIds(ids);
    }

    /**
     * 删除平台配置信息
     * 
     * @param id 平台配置主键
     * @return 结果
     */
    @Override
    public int deleteWakConfigById(Integer id)
    {
        return wakConfigMapper.deleteWakConfigById(id);
    }

    @Override
    public WakConfig selectWakConfigByName(String name) {
        return wakConfigMapper.selectWakConfigByName(name);
    }

    @Override
    public String selectWakAuthaddressByName(String searchName) {
        WakConfig wakConfig = wakConfigMapper.selectWakConfigByName(searchName);
        return wakConfig.getValue();
    }
}
