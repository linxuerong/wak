package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakAddresschu;

/**
 * 出款地址管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakAddresschuMapper 
{
    /**
     * 查询出款地址管理
     * 
     * @param id 出款地址管理主键
     * @return 出款地址管理
     */
    public WakAddresschu selectWakAddresschuById(Long id);

    /**
     * 查询出款地址管理列表
     * 
     * @param wakAddresschu 出款地址管理
     * @return 出款地址管理集合
     */
    public List<WakAddresschu> selectWakAddresschuList(WakAddresschu wakAddresschu);

    /**
     * 新增出款地址管理
     * 
     * @param wakAddresschu 出款地址管理
     * @return 结果
     */
    public int insertWakAddresschu(WakAddresschu wakAddresschu);

    /**
     * 修改出款地址管理
     * 
     * @param wakAddresschu 出款地址管理
     * @return 结果
     */
    public int updateWakAddresschu(WakAddresschu wakAddresschu);

    /**
     * 删除出款地址管理
     * 
     * @param id 出款地址管理主键
     * @return 结果
     */
    public int deleteWakAddresschuById(Long id);

    /**
     * 批量删除出款地址管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakAddresschuByIds(Long[] ids);

    WakAddresschu selectWakAddresschuByType(String type);
}
