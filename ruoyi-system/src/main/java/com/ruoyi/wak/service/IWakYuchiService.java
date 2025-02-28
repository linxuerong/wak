package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakYuchi;

/**
 * 鱼池Service接口
 *
 * @author ruoyi
 * @date 2025-01-24
 */
public interface IWakYuchiService
{
    /**
     * 查询鱼池
     *
     * @param userId 鱼池主键
     * @return 鱼池
     */
    public WakYuchi selectWakYuchiByUserId(Long userId);

    /**
     * 查询鱼池列表
     *
     * @param wakYuchi 鱼池
     * @return 鱼池集合
     */
    public List<WakYuchi> selectWakYuchiList(WakYuchi wakYuchi);

    /**
     * 新增鱼池
     *
     * @param wakYuchi 鱼池
     * @return 结果
     */
    public int insertWakYuchi(WakYuchi wakYuchi);

    /**
     * 修改鱼池
     *
     * @param wakYuchi 鱼池
     * @return 结果
     */
    public int updateWakYuchi(WakYuchi wakYuchi);

    /**
     * 批量删除鱼池
     *
     * @param userIds 需要删除的鱼池主键集合
     * @return 结果
     */
    public int deleteWakYuchiByUserIds(Long[] userIds);

    /**
     * 删除鱼池信息
     *
     * @param userId 鱼池主键
     * @return 结果
     */
    public int deleteWakYuchiByUserId(Long userId);
}
