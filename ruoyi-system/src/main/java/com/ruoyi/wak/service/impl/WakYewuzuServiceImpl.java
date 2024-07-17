package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakYewuzuMapper;
import com.ruoyi.wak.domain.WakYewuzu;
import com.ruoyi.wak.service.IWakYewuzuService;

/**
 * 业务组Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class WakYewuzuServiceImpl implements IWakYewuzuService 
{
    @Autowired
    private WakYewuzuMapper wakYewuzuMapper;

    /**
     * 查询业务组
     * 
     * @param id 业务组主键
     * @return 业务组
     */
    @Override
    public WakYewuzu selectWakYewuzuById(Long id)
    {
        return wakYewuzuMapper.selectWakYewuzuById(id);
    }

    /**
     * 查询业务组列表
     * 
     * @param wakYewuzu 业务组
     * @return 业务组
     */
    @Override
    public List<WakYewuzu> selectWakYewuzuList(WakYewuzu wakYewuzu)
    {
        return wakYewuzuMapper.selectWakYewuzuList(wakYewuzu);
    }

    /**
     * 新增业务组
     * 
     * @param wakYewuzu 业务组
     * @return 结果
     */
    @Override
    public int insertWakYewuzu(WakYewuzu wakYewuzu)
    {
        return wakYewuzuMapper.insertWakYewuzu(wakYewuzu);
    }

    /**
     * 修改业务组
     * 
     * @param wakYewuzu 业务组
     * @return 结果
     */
    @Override
    public int updateWakYewuzu(WakYewuzu wakYewuzu)
    {
        return wakYewuzuMapper.updateWakYewuzu(wakYewuzu);
    }

    /**
     * 批量删除业务组
     * 
     * @param ids 需要删除的业务组主键
     * @return 结果
     */
    @Override
    public int deleteWakYewuzuByIds(Long[] ids)
    {
        return wakYewuzuMapper.deleteWakYewuzuByIds(ids);
    }

    /**
     * 删除业务组信息
     * 
     * @param id 业务组主键
     * @return 结果
     */
    @Override
    public int deleteWakYewuzuById(Long id)
    {
        return wakYewuzuMapper.deleteWakYewuzuById(id);
    }
}
