package com.ruoyi.wak.mapper;

import java.util.List;
import com.ruoyi.wak.domain.WakAuthaddress;

/**
 * 授权列Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface WakAuthaddressMapper 
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

    /**
     * 删除授权列
     * 
     * @param id 授权列主键
     * @return 结果
     */
    public int deleteWakAuthaddressById(Long id);

    /**
     * 批量删除授权列
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWakAuthaddressByIds(Long[] ids);

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
