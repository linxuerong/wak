package com.ruoyi.wak.service.impl;

import java.util.List;

import com.ruoyi.wak.vo.WakYewuyuanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakYewuyuanMapper;
import com.ruoyi.wak.domain.WakYewuyuan;
import com.ruoyi.wak.service.IWakYewuyuanService;

/**
 * 业务员Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class WakYewuyuanServiceImpl implements IWakYewuyuanService 
{
    @Autowired
    private WakYewuyuanMapper wakYewuyuanMapper;

    /**
     * 查询业务员
     * 
     * @param id 业务员主键
     * @return 业务员
     */
    @Override
    public WakYewuyuan selectWakYewuyuanById(Long id)
    {
        return wakYewuyuanMapper.selectWakYewuyuanById(id);
    }

    /**
     * 查询业务员列表
     * 
     * @param wakYewuyuan 业务员
     * @return 业务员
     */
    @Override
    public List<WakYewuyuanVo> selectWakYewuyuanList(WakYewuyuan wakYewuyuan)
    {
        return wakYewuyuanMapper.selectWakYewuyuanList(wakYewuyuan);
    }

    /**
     * 新增业务员
     * 
     * @param wakYewuyuan 业务员
     * @return 结果
     */
    @Override
    public int insertWakYewuyuan(WakYewuyuan wakYewuyuan)
    {
        return wakYewuyuanMapper.insertWakYewuyuan(wakYewuyuan);
    }

    /**
     * 修改业务员
     * 
     * @param wakYewuyuan 业务员
     * @return 结果
     */
    @Override
    public int updateWakYewuyuan(WakYewuyuan wakYewuyuan)
    {
        return wakYewuyuanMapper.updateWakYewuyuan(wakYewuyuan);
    }

    /**
     * 批量删除业务员
     * 
     * @param ids 需要删除的业务员主键
     * @return 结果
     */
    @Override
    public int deleteWakYewuyuanByIds(Long[] ids)
    {
        return wakYewuyuanMapper.deleteWakYewuyuanByIds(ids);
    }

    /**
     * 删除业务员信息
     * 
     * @param id 业务员主键
     * @return 结果
     */
    @Override
    public int deleteWakYewuyuanById(Long id)
    {
        return wakYewuyuanMapper.deleteWakYewuyuanById(id);
    }
}
