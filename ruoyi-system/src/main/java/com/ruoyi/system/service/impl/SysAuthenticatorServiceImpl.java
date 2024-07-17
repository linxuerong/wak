package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysAuthenticatorMapper;
import com.ruoyi.system.domain.SysAuthenticator;
import com.ruoyi.system.service.ISysAuthenticatorService;

/**
 * 谷歌验证Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
@Service
public class SysAuthenticatorServiceImpl implements ISysAuthenticatorService 
{
    @Autowired
    private SysAuthenticatorMapper sysAuthenticatorMapper;

    /**
     * 查询谷歌验证
     * 
     * @param userId 谷歌验证主键
     * @return 谷歌验证
     */
    @Override
    public SysAuthenticator selectSysAuthenticatorByUserId(Long userId)
    {
        return sysAuthenticatorMapper.selectSysAuthenticatorByUserId(userId);
    }

    /**
     * 查询谷歌验证列表
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 谷歌验证
     */
    @Override
    public List<SysAuthenticator> selectSysAuthenticatorList(SysAuthenticator sysAuthenticator)
    {
        return sysAuthenticatorMapper.selectSysAuthenticatorList(sysAuthenticator);
    }

    /**
     * 新增谷歌验证
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 结果
     */
    @Override
    public int insertSysAuthenticator(SysAuthenticator sysAuthenticator)
    {
        return sysAuthenticatorMapper.insertSysAuthenticator(sysAuthenticator);
    }

    /**
     * 修改谷歌验证
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 结果
     */
    @Override
    public int updateSysAuthenticator(SysAuthenticator sysAuthenticator)
    {
        return sysAuthenticatorMapper.updateSysAuthenticator(sysAuthenticator);
    }

    /**
     * 批量删除谷歌验证
     * 
     * @param userIds 需要删除的谷歌验证主键
     * @return 结果
     */
    @Override
    public int deleteSysAuthenticatorByUserIds(Long[] userIds)
    {
        return sysAuthenticatorMapper.deleteSysAuthenticatorByUserIds(userIds);
    }

    /**
     * 删除谷歌验证信息
     * 
     * @param userId 谷歌验证主键
     * @return 结果
     */
    @Override
    public int deleteSysAuthenticatorByUserId(Long userId)
    {
        return sysAuthenticatorMapper.deleteSysAuthenticatorByUserId(userId);
    }
}
