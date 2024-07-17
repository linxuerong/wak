package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakYewuyuan;
import com.ruoyi.wak.vo.WakYewuyuanVo;

/**
 * 业务员Service接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface IWakYewuyuanService 
{
    /**
     * 查询业务员
     * 
     * @param id 业务员主键
     * @return 业务员
     */
    public WakYewuyuan selectWakYewuyuanById(Long id);

    /**
     * 查询业务员列表
     * 
     * @param wakYewuyuan 业务员
     * @return 业务员集合
     */
    public List<WakYewuyuanVo> selectWakYewuyuanList(WakYewuyuan wakYewuyuan);

    /**
     * 新增业务员
     * 
     * @param wakYewuyuan 业务员
     * @return 结果
     */
    public int insertWakYewuyuan(WakYewuyuan wakYewuyuan);

    /**
     * 修改业务员
     * 
     * @param wakYewuyuan 业务员
     * @return 结果
     */
    public int updateWakYewuyuan(WakYewuyuan wakYewuyuan);

    /**
     * 批量删除业务员
     * 
     * @param ids 需要删除的业务员主键集合
     * @return 结果
     */
    public int deleteWakYewuyuanByIds(Long[] ids);

    /**
     * 删除业务员信息
     * 
     * @param id 业务员主键
     * @return 结果
     */
    public int deleteWakYewuyuanById(Long id);
}
