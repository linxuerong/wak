package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakUserRecord;

/**
 * 用户操作记录表Service接口
 * 
 * @author ruoyi
 * @date 2024-06-04
 */
public interface IWakUserRecordService 
{
    /**
     * 查询用户操作记录表
     * 
     * @param id 用户操作记录表主键
     * @return 用户操作记录表
     */
    public WakUserRecord selectWakUserRecordById(Long id);

    /**
     * 查询用户操作记录表列表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 用户操作记录表集合
     */
    public List<WakUserRecord> selectWakUserRecordList(WakUserRecord wakUserRecord);

    /**
     * 新增用户操作记录表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 结果
     */
    public int insertWakUserRecord(WakUserRecord wakUserRecord);

    /**
     * 修改用户操作记录表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 结果
     */
    public int updateWakUserRecord(WakUserRecord wakUserRecord);

    /**
     * 批量删除用户操作记录表
     * 
     * @param ids 需要删除的用户操作记录表主键集合
     * @return 结果
     */
    public int deleteWakUserRecordByIds(Long[] ids);

    /**
     * 删除用户操作记录表信息
     * 
     * @param id 用户操作记录表主键
     * @return 结果
     */
    public int deleteWakUserRecordById(Long id);
}
