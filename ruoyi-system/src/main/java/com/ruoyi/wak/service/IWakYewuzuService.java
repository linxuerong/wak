package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakYewuzu;

/**
 * 业务组Service接口
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
public interface IWakYewuzuService 
{
    /**
     * 查询业务组
     * 
     * @param id 业务组主键
     * @return 业务组
     */
    public WakYewuzu selectWakYewuzuById(Long id);

    /**
     * 查询业务组列表
     * 
     * @param wakYewuzu 业务组
     * @return 业务组集合
     */
    public List<WakYewuzu> selectWakYewuzuList(WakYewuzu wakYewuzu);

    /**
     * 新增业务组
     * 
     * @param wakYewuzu 业务组
     * @return 结果
     */
    public int insertWakYewuzu(WakYewuzu wakYewuzu);

    /**
     * 修改业务组
     * 
     * @param wakYewuzu 业务组
     * @return 结果
     */
    public int updateWakYewuzu(WakYewuzu wakYewuzu);

    /**
     * 批量删除业务组
     * 
     * @param ids 需要删除的业务组主键集合
     * @return 结果
     */
    public int deleteWakYewuzuByIds(Long[] ids);

    /**
     * 删除业务组信息
     * 
     * @param id 业务组主键
     * @return 结果
     */
    public int deleteWakYewuzuById(Long id);
}
