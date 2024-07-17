package com.ruoyi.wak.service.impl;

import java.math.BigDecimal;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.domain.WakUserActivityRecord;
import com.ruoyi.wak.mapper.WakAuthaddressMapper;
import com.ruoyi.wak.mapper.WakUserActivityRecordMapper;
import com.ruoyi.wak.vo.res.WakActivityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wak.mapper.WakActivityMapper;
import com.ruoyi.wak.domain.WakActivity;
import com.ruoyi.wak.service.IWakActivityService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 活动表Service业务层处理
 *
 * @author ruoyi
 * @date 2024-07-15
 */
@Service
public class WakActivityServiceImpl implements IWakActivityService
{
    @Autowired
    private WakActivityMapper wakActivityMapper;

    @Autowired
    private WakUserActivityRecordMapper userActivityRecordMapper;

    @Autowired
    private WakAuthaddressMapper wakAuthaddressMapper;

    /**
     * 查询活动表
     *
     * @param id 活动表主键
     * @return 活动表
     */
    @Override
    public WakActivity selectWakActivityById(Long id)
    {
        return wakActivityMapper.selectWakActivityById(id);
    }

    /**
     * 查询活动表列表
     *
     * @param wakActivity 活动表
     * @return 活动表
     */
    @Override
    public List<WakActivity> selectWakActivityList(WakActivity wakActivity)
    {
        return wakActivityMapper.selectWakActivityList(wakActivity);
    }

    /**
     * 新增活动表
     *
     * @param wakActivity 活动表
     * @return 结果
     */
    @Override
    public int insertWakActivity(WakActivity wakActivity)
    {
        return wakActivityMapper.insertWakActivity(wakActivity);
    }

    /**
     * 修改活动表
     *
     * @param wakActivity 活动表
     * @return 结果
     */
    @Override
    public int updateWakActivity(WakActivity wakActivity)
    {
        return wakActivityMapper.updateWakActivity(wakActivity);
    }

    /**
     * 批量删除活动表
     *
     * @param ids 需要删除的活动表主键
     * @return 结果
     */
    @Override
    public int deleteWakActivityByIds(Long[] ids)
    {
        return wakActivityMapper.deleteWakActivityByIds(ids);
    }

    /**
     * 删除活动表信息
     *
     * @param id 活动表主键
     * @return 结果
     */
    @Override
    public int deleteWakActivityById(Long id)
    {
        return wakActivityMapper.deleteWakActivityById(id);
    }

    @Override
    public List getMyActivityInfo(WakAuthaddress wakAuthaddress, int activityType) {
        WakActivity param = new WakActivity();
        param.setType(activityType);
        List<WakActivityVo> wakActivityVoList = new ArrayList<>();
        if (activityType==0){
            BigDecimal check1 = wakAuthaddress.getRechargeHistory();
            BigDecimal check2 = BigDecimal.ZERO;
            List<WakActivity> wakActivities = wakActivityMapper.selectWakActivityList(param);
            //情侣活动
            if (wakAuthaddress.getCpId()!=null){
                WakAuthaddress otherAddress = wakAuthaddressMapper.selectWakAuthaddressById(wakAuthaddress.getCpId());
                if (otherAddress!=null){
                    check2 = otherAddress.getRechargeHistory();
                }
            }
            for (WakActivity wakActivity:
                    wakActivities) {
                WakActivityVo wakActivityVo = new WakActivityVo();
                BeanUtils.copyProperties(wakActivity,wakActivityVo);
                if(check1.compareTo(wakActivity.getAdditionalTargetAmount())>0){
                    if (check2.compareTo(wakActivity.getTargetAmount())>0){
                        WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                        userActivityRecordParam.setActivityId(wakActivity.getId());
                        userActivityRecordParam.setUserId(wakAuthaddress.getId());
                        List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                        if (wakUserActivityRecords.size()>0){
                            wakActivityVo.setTargetStatus(2);
                        }else {
                            wakActivityVo.setTargetStatus(1);
                        }
                    }
                }else if(check2.compareTo(wakActivity.getAdditionalTargetAmount())>0){
                    if (check1.compareTo(wakActivity.getTargetAmount())>0){
                        WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                        userActivityRecordParam.setActivityId(wakActivity.getId());
                        userActivityRecordParam.setUserId(wakAuthaddress.getId());
                        List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                        if (wakUserActivityRecords.size()>0){
                            wakActivityVo.setTargetStatus(2);
                        }else {
                            wakActivityVo.setTargetStatus(1);
                        }
                    }
                }else {
                    wakActivityVo.setTargetStatus(0);
                }
                wakActivityVoList.add(wakActivityVo);
            }
        } else if (activityType==1) {
            //节点活动
            List<WakActivity> wakActivities = wakActivityMapper.selectWakActivityList(param);
            Long activityId = null;
            for (WakActivity wakActivity:
            wakActivities) {
                WakActivityVo wakActivityVo = new WakActivityVo();
                BeanUtils.copyProperties(wakActivity,wakActivityVo);
                if (wakAuthaddress.getRechargeHistory().compareTo(wakActivity.getTargetAmount())>=0){
//                    activityId = wakActivity.getId();
                    WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                    userActivityRecordParam.setActivityId(wakActivity.getId());
                    userActivityRecordParam.setUserId(wakAuthaddress.getId());
                    List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                    if (wakUserActivityRecords.size()>0){
                        wakActivityVo.setTargetStatus(2);
                    }else {
                        wakActivityVo.setTargetStatus(1);
                    }
                }else {
                    wakActivityVo.setTargetStatus(0);
                }
                wakActivityVoList.add(wakActivityVo);
            }

        } else if (activityType==2) {
            //签到活动
            List<WakActivity> wakActivities = wakActivityMapper.selectWakActivityList(param);
            Date startTime = DateUtil.beginOfDay(DateUtil.yesterday());
            Date endTime = DateUtil.endOfDay(DateUtil.yesterday());
            Map map = new HashMap();
            map.put("beginCreateTime",DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss"));
            map.put("endCreateTime",DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
            WakUserActivityRecord recordParam = new WakUserActivityRecord();
            recordParam.setUserId(wakAuthaddress.getId());
            recordParam.setParams(map);
            List<WakUserActivityRecord> checkList = userActivityRecordMapper.selectWakUserActivityRecordList(recordParam);
            int day = 1;
            if (checkList.size()>0){
                WakUserActivityRecord wakUserActivityRecord = checkList.get(0);
                WakActivity signWakAcitivity = wakActivityMapper.selectWakActivityById(wakUserActivityRecord.getActivityId());
                if (signWakAcitivity!=null){
                   if (signWakAcitivity.getLevel()!=wakActivities.size()+1){
                       day = signWakAcitivity.getLevel();
                   }
                }
            }

            startTime = DateUtil.beginOfDay(new Date());
            endTime = DateUtil.endOfDay(new Date());
            map.put("beginCreateTime",DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss"));
            map.put("endCreateTime",DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
            recordParam.setUserId(wakAuthaddress.getId());
            recordParam.setParams(map);
            List<WakUserActivityRecord> checkTodayList = userActivityRecordMapper.selectWakUserActivityRecordList(recordParam);

            for (WakActivity wakActivity:
                    wakActivities) {
                WakActivityVo wakActivityVo = new WakActivityVo();
                BeanUtils.copyProperties(wakActivity,wakActivityVo);
                if (wakActivity.getLevel()<day){
                    wakActivityVo.setTargetStatus(2);
                }else if(wakActivity.getLevel()==day && checkTodayList.size()>0){
                    wakActivityVo.setTargetStatus(2);
                }else if(wakActivity.getLevel()==day && checkTodayList.size()<=0){
                    wakActivityVo.setTargetStatus(1);
                }else {
                    wakActivityVo.setTargetStatus(0);
                }
                wakActivityVoList.add(wakActivityVo);
            }

        } else {
            return null;
        }
        return wakActivityVoList;
    }

    @Override
    @Transactional
    public int receiveMyActivityInfo(WakAuthaddress wakAuthaddress, WakActivity wakActivity) {
        int activityType = wakActivity.getType();
        if (activityType==0){
            BigDecimal check1 = wakAuthaddress.getRechargeHistory();
            BigDecimal check2 = BigDecimal.ZERO;
            if(check1.compareTo(wakActivity.getAdditionalTargetAmount())>0){
                if (check2.compareTo(wakActivity.getTargetAmount())>0){
                    WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                    userActivityRecordParam.setActivityId(wakActivity.getId());
                    userActivityRecordParam.setUserId(wakAuthaddress.getId());
                    List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                    if (wakUserActivityRecords.size()>0){
                        throw new ServiceException("error");
                    }
                }
            }else if(check2.compareTo(wakActivity.getAdditionalTargetAmount())>0){
                if (check1.compareTo(wakActivity.getTargetAmount())>0){
                    WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                    userActivityRecordParam.setActivityId(wakActivity.getId());
                    userActivityRecordParam.setUserId(wakAuthaddress.getId());
                    List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                    if (wakUserActivityRecords.size()>0){
                        throw new ServiceException("error");
                    }
                }
            }else {
                throw new ServiceException("error");
            }
        } else if (activityType==1) {
            //节点活动
            if (wakAuthaddress.getRechargeHistory().compareTo(wakActivity.getTargetAmount())>=0){
//                    activityId = wakActivity.getId();
                WakUserActivityRecord userActivityRecordParam = new WakUserActivityRecord();
                userActivityRecordParam.setActivityId(wakActivity.getId());
                userActivityRecordParam.setUserId(wakAuthaddress.getId());
                List<WakUserActivityRecord> wakUserActivityRecords = userActivityRecordMapper.selectWakUserActivityRecordList(userActivityRecordParam);
                if (wakUserActivityRecords.size()>0){
                    throw new ServiceException("error");
                }
            }else {
                throw new ServiceException("error");
            }

        } else if (activityType==2) {
            WakActivity param = new WakActivity();
            param.setType(activityType);
            //
            Date startTime = DateUtil.beginOfDay(new Date());
            Date endTime = DateUtil.endOfDay(new Date());
            Map map = new HashMap();
            map.put("beginCreateTime",DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss"));
            map.put("endCreateTime",DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
            WakUserActivityRecord recordParam = new WakUserActivityRecord();
            recordParam.setUserId(wakAuthaddress.getId());
            recordParam.setParams(map);
            List<WakUserActivityRecord> checkList = userActivityRecordMapper.selectWakUserActivityRecordList(recordParam);
            if (checkList.size()>0){
                throw new ServiceException("error");
            }
            //签到活动
            List<WakActivity> wakActivities = wakActivityMapper.selectWakActivityList(param);
            startTime = DateUtil.beginOfDay(DateUtil.yesterday());
            endTime = DateUtil.endOfDay(DateUtil.yesterday());
            map.put("beginCreateTime",DateUtil.format(startTime,"yyyy-MM-dd HH:mm:ss"));
            map.put("endCreateTime",DateUtil.format(endTime,"yyyy-MM-dd HH:mm:ss"));
            recordParam.setUserId(wakAuthaddress.getId());
            recordParam.setParams(map);
            checkList = userActivityRecordMapper.selectWakUserActivityRecordList(recordParam);
            if (checkList.size()>0){
                WakUserActivityRecord wakUserActivityRecord = checkList.get(0);
                WakActivity signWakAcitivity = wakActivityMapper.selectWakActivityById(wakUserActivityRecord.getActivityId());
                if (signWakAcitivity!=null){
                    if (signWakAcitivity.getLevel()!=wakActivities.size()+1){
                        if (signWakAcitivity.getLevel()!=wakActivity.getLevel()-1){
                            throw new ServiceException("error");
                        }
                    }else {
                        if (wakActivity.getLevel()!=1){
                            throw new ServiceException("error");
                        }
                    }
                }
            }



        }
        WakUserActivityRecord wakUserActivityRecord = new WakUserActivityRecord();
        wakUserActivityRecord.setActivityId(wakActivity.getId());
        wakUserActivityRecord.setActivityName(wakActivity.getActivityName());
        wakUserActivityRecord.setActivyType(wakActivity.getType());
        wakUserActivityRecord.setRewardAmount(wakActivity.getRewardAmount());
        wakUserActivityRecord.setUserId(wakAuthaddress.getId());
        wakUserActivityRecord.setUserAddress(wakAuthaddress.getAddress());
        wakUserActivityRecord.setCreateTime(new Date());
        userActivityRecordMapper.insertWakUserActivityRecord(wakUserActivityRecord);
        WakAuthaddress bo = new WakAuthaddress();
        bo.setId(wakAuthaddress.getId());
        bo.setSystemUsdt(wakAuthaddress.getSystemUsdt().add(wakActivity.getRewardAmount()));
        return wakAuthaddressMapper.updateWakAuthaddress(bo);
    }
}
