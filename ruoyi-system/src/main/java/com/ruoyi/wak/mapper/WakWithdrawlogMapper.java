package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakWithdrawlog;

/**
 * 用户提现日志Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakWithdrawlogMapper 
{
    /**
     * 查询用户提现日志
     * 
     * @param id 用户提现日志主键
     * @return 用户提现日志
     */
    public WakWithdrawlog selectWakWithdrawlogById(Long id);

    /**
     * 查询用户提现日志列表
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 用户提现日志集合
     */
    public List<WakWithdrawlog> selectWakWithdrawlogList(WakWithdrawlog wakWithdrawlog);

    /**
     * 新增用户提现日志
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 结果
     */
    public int insertWakWithdrawlog(WakWithdrawlog wakWithdrawlog);

    /**
     * 修改用户提现日志
     * 
     * @param wakWithdrawlog 用户提现日志
     * @return 结果
     */
    public int updateWakWithdrawlog(WakWithdrawlog wakWithdrawlog);

    /**
     * 删除用户提现日志
     * 
     * @param id 用户提现日志主键
     * @return 结果
     */
    public int deleteWakWithdrawlogById(Long id);

    /**
     * 批量删除用户提现日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakWithdrawlogByIds(Long[] ids);
}
