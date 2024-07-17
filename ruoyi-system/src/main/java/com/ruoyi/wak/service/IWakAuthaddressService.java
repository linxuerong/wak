package com.ruoyi.wak.service;

import java.util.List;
import com.ruoyi.wak.domain.WakAuthaddress;

/**
 * 授权列Service接口
 *
 * @author ruoyi
 * @date 2023-12-11
 */
public interface IWakAuthaddressService
{
    /**
     * 查询授权列
     *
     * @param id 授权列主键
     * @return 授权列
     */
    public WakAuthaddress selectWakAuthaddressById(Long id);

    /**
     * 查询授权列列表
     *
     * @param wakAuthaddress 授权列
     * @return 授权列集合
     */
    public List<WakAuthaddress> selectWakAuthaddressList(WakAuthaddress wakAuthaddress);

    /**
     * 新增授权列
     *
     * @param wakAuthaddress 授权列
     * @return 结果
     */
    public int insertWakAuthaddress(WakAuthaddress wakAuthaddress);

    /**
     * 修改授权列
     *
     * @param wakAuthaddress 授权列
     * @return 结果
     */
    public int updateWakAuthaddress(WakAuthaddress wakAuthaddress);
    public int updateWakAuthaddress2(WakAuthaddress wakAuthaddress);

    /**
     * 批量删除授权列
     *
     * @param ids 需要删除的授权列主键集合
     * @return 结果
     */
    public int deleteWakAuthaddressByIds(Long[] ids);

    /**
     * 删除授权列信息
     *
     * @param id 授权列主键
     * @return 结果
     */
    public int deleteWakAuthaddressById(Long id);

    /**
     * 通过邀请码查询上级
     * @param invite
     * @return
     */
    WakAuthaddress selectWakAuthaddressByInviteCode(String invite);

    /**
     * 通过地址查询
     * @param address
     * @return
     */
    WakAuthaddress selectWakAuthaddressByAddress(String address);

    /**
     * 获取有余额的地址
     * @return
     */
    List<WakAuthaddress> selectAliveWakAuthaddressList();
}
