package com.ruoyi.wak.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakUserRecordMapper;
import com.ruoyi.wak.domain.WakUserRecord;
import com.ruoyi.wak.service.IWakUserRecordService;

/**
 * 用户操作记录表Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-04
 */
@Service
public class WakUserRecordServiceImpl implements IWakUserRecordService 
{
    @Autowired
    private WakUserRecordMapper wakUserRecordMapper;

    /**
     * 查询用户操作记录表
     * 
     * @param id 用户操作记录表主键
     * @return 用户操作记录表
     */
    @Override
    public WakUserRecord selectWakUserRecordById(Long id)
    {
        return wakUserRecordMapper.selectWakUserRecordById(id);
    }

    /**
     * 查询用户操作记录表列表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 用户操作记录表
     */
    @Override
    public List<WakUserRecord> selectWakUserRecordList(WakUserRecord wakUserRecord)
    {
        return wakUserRecordMapper.selectWakUserRecordList(wakUserRecord);
    }

    /**
     * 新增用户操作记录表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 结果
     */
    @Override
    public int insertWakUserRecord(WakUserRecord wakUserRecord)
    {
        wakUserRecord.setCreateTime(DateUtils.getNowDate());
        return wakUserRecordMapper.insertWakUserRecord(wakUserRecord);
    }

    /**
     * 修改用户操作记录表
     * 
     * @param wakUserRecord 用户操作记录表
     * @return 结果
     */
    @Override
    public int updateWakUserRecord(WakUserRecord wakUserRecord)
    {
        return wakUserRecordMapper.updateWakUserRecord(wakUserRecord);
    }

    /**
     * 批量删除用户操作记录表
     * 
     * @param ids 需要删除的用户操作记录表主键
     * @return 结果
     */
    @Override
    public int deleteWakUserRecordByIds(Long[] ids)
    {
        return wakUserRecordMapper.deleteWakUserRecordByIds(ids);
    }

    /**
     * 删除用户操作记录表信息
     * 
     * @param id 用户操作记录表主键
     * @return 结果
     */
    @Override
    public int deleteWakUserRecordById(Long id)
    {
        return wakUserRecordMapper.deleteWakUserRecordById(id);
    }
}
