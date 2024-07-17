package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakTongjiMapper;
import com.ruoyi.wak.domain.WakTongji;
import com.ruoyi.wak.service.IWakTongjiService;

/**
 * 统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@Service
public class WakTongjiServiceImpl implements IWakTongjiService 
{
    @Autowired
    private WakTongjiMapper wakTongjiMapper;

    /**
     * 查询统计
     * 
     * @param id 统计主键
     * @return 统计
     */
    @Override
    public WakTongji selectWakTongjiById(Long id)
    {
        return wakTongjiMapper.selectWakTongjiById(id);
    }

    /**
     * 查询统计列表
     * 
     * @param wakTongji 统计
     * @return 统计
     */
    @Override
    public List<WakTongji> selectWakTongjiList(WakTongji wakTongji)
    {
        return wakTongjiMapper.selectWakTongjiList(wakTongji);
    }

    /**
     * 新增统计
     * 
     * @param wakTongji 统计
     * @return 结果
     */
    @Override
    public int insertWakTongji(WakTongji wakTongji)
    {
        return wakTongjiMapper.insertWakTongji(wakTongji);
    }

    /**
     * 修改统计
     * 
     * @param wakTongji 统计
     * @return 结果
     */
    @Override
    public int updateWakTongji(WakTongji wakTongji)
    {
        return wakTongjiMapper.updateWakTongji(wakTongji);
    }

    /**
     * 批量删除统计
     * 
     * @param ids 需要删除的统计主键
     * @return 结果
     */
    @Override
    public int deleteWakTongjiByIds(Long[] ids)
    {
        return wakTongjiMapper.deleteWakTongjiByIds(ids);
    }

    /**
     * 删除统计信息
     * 
     * @param id 统计主键
     * @return 结果
     */
    @Override
    public int deleteWakTongjiById(Long id)
    {
        return wakTongjiMapper.deleteWakTongjiById(id);
    }
}
