package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakTongji;

/**
 * 统计Service接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface IWakTongjiService 
{
    /**
     * 查询统计
     * 
     * @param id 统计主键
     * @return 统计
     */
    public WakTongji selectWakTongjiById(Long id);

    /**
     * 查询统计列表
     * 
     * @param wakTongji 统计
     * @return 统计集合
     */
    public List<WakTongji> selectWakTongjiList(WakTongji wakTongji);

    /**
     * 新增统计
     * 
     * @param wakTongji 统计
     * @return 结果
     */
    public int insertWakTongji(WakTongji wakTongji);

    /**
     * 修改统计
     * 
     * @param wakTongji 统计
     * @return 结果
     */
    public int updateWakTongji(WakTongji wakTongji);

    /**
     * 批量删除统计
     * 
     * @param ids 需要删除的统计主键集合
     * @return 结果
     */
    public int deleteWakTongjiByIds(Long[] ids);

    /**
     * 删除统计信息
     * 
     * @param id 统计主键
     * @return 结果
     */
    public int deleteWakTongjiById(Long id);
}
