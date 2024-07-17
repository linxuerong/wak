package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakTransferlog;

/**
 * 转账记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakTransferlogMapper 
{
    /**
     * 查询转账记录
     * 
     * @param id 转账记录主键
     * @return 转账记录
     */
    public WakTransferlog selectWakTransferlogById(Long id);

    /**
     * 查询转账记录列表
     * 
     * @param wakTransferlog 转账记录
     * @return 转账记录集合
     */
    public List<WakTransferlog> selectWakTransferlogList(WakTransferlog wakTransferlog);

    /**
     * 新增转账记录
     * 
     * @param wakTransferlog 转账记录
     * @return 结果
     */
    public int insertWakTransferlog(WakTransferlog wakTransferlog);

    /**
     * 修改转账记录
     * 
     * @param wakTransferlog 转账记录
     * @return 结果
     */
    public int updateWakTransferlog(WakTransferlog wakTransferlog);

    /**
     * 删除转账记录
     * 
     * @param id 转账记录主键
     * @return 结果
     */
    public int deleteWakTransferlogById(Long id);

    /**
     * 批量删除转账记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakTransferlogByIds(Long[] ids);
}
