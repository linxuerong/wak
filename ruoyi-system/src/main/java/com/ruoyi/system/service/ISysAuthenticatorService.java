package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysAuthenticator;

/**
 * 谷歌验证Service接口
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
public interface ISysAuthenticatorService 
{
    /**
     * 查询谷歌验证
     * 
     * @param userId 谷歌验证主键
     * @return 谷歌验证
     */
    public SysAuthenticator selectSysAuthenticatorByUserId(Long userId);

    /**
     * 查询谷歌验证列表
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 谷歌验证集合
     */
    public List<SysAuthenticator> selectSysAuthenticatorList(SysAuthenticator sysAuthenticator);

    /**
     * 新增谷歌验证
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 结果
     */
    public int insertSysAuthenticator(SysAuthenticator sysAuthenticator);

    /**
     * 修改谷歌验证
     * 
     * @param sysAuthenticator 谷歌验证
     * @return 结果
     */
    public int updateSysAuthenticator(SysAuthenticator sysAuthenticator);

    /**
     * 批量删除谷歌验证
     * 
     * @param userIds 需要删除的谷歌验证主键集合
     * @return 结果
     */
    public int deleteSysAuthenticatorByUserIds(Long[] userIds);

    /**
     * 删除谷歌验证信息
     * 
     * @param userId 谷歌验证主键
     * @return 结果
     */
    public int deleteSysAuthenticatorByUserId(Long userId);
}
