package com.ruoyi.wak.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakTransferlogMapper;
import com.ruoyi.wak.domain.WakTransferlog;
import com.ruoyi.wak.service.IWakTransferlogService;

/**
 * 转账记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class WakTransferlogServiceImpl implements IWakTransferlogService
{
    @Autowired
    private WakTransferlogMapper wakTransferlogMapper;

    /**
     * 查询转账记录
     *
     * @param id 转账记录主键
     * @return 转账记录
     */
    @Override
    public WakTransferlog selectWakTransferlogById(Long id)
    {
        return wakTransferlogMapper.selectWakTransferlogById(id);
    }

    /**
     * 查询转账记录列表
     *
     * @param wakTransferlog 转账记录
     * @return 转账记录
     */
    @Override
    public List<WakTransferlog> selectWakTransferlogList(WakTransferlog wakTransferlog)
    {
        return wakTransferlogMapper.selectWakTransferlogList(wakTransferlog);
    }

    /**
     * 新增转账记录
     *
     * @param wakTransferlog 转账记录
     * @return 结果
     */
    @Override
    public int insertWakTransferlog(WakTransferlog wakTransferlog)
    {
        wakTransferlog.setCreatetime(System.currentTimeMillis()/1000);
        return wakTransferlogMapper.insertWakTransferlog(wakTransferlog);
    }

    /**
     * 修改转账记录
     *
     * @param wakTransferlog 转账记录
     * @return 结果
     */
    @Override
    public int updateWakTransferlog(WakTransferlog wakTransferlog)
    {
        return wakTransferlogMapper.updateWakTransferlog(wakTransferlog);
    }

    /**
     * 批量删除转账记录
     *
     * @param ids 需要删除的转账记录主键
     * @return 结果
     */
    @Override
    public int deleteWakTransferlogByIds(Long[] ids)
    {
        return wakTransferlogMapper.deleteWakTransferlogByIds(ids);
    }

    /**
     * 删除转账记录信息
     *
     * @param id 转账记录主键
     * @return 结果
     */
    @Override
    public int deleteWakTransferlogById(Long id)
    {
        return wakTransferlogMapper.deleteWakTransferlogById(id);
    }
}
