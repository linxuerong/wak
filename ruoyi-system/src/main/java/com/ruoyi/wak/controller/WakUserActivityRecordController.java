package com.ruoyi.wak.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wak.domain.WakUserActivityRecord;
import com.ruoyi.wak.service.IWakUserActivityRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户活动记录Controller
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/wak/activityRecord")
public class WakUserActivityRecordController extends BaseController
{
    @Autowired
    private IWakUserActivityRecordService wakUserActivityRecordService;

    /**
     * 查询用户活动记录列表
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakUserActivityRecord wakUserActivityRecord)
    {
        startPage();
        List<WakUserActivityRecord> list = wakUserActivityRecordService.selectWakUserActivityRecordList(wakUserActivityRecord);
        return getDataTable(list);
    }

    /**
     * 导出用户活动记录列表
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:export')")
    @Log(title = "用户活动记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakUserActivityRecord wakUserActivityRecord)
    {
        List<WakUserActivityRecord> list = wakUserActivityRecordService.selectWakUserActivityRecordList(wakUserActivityRecord);
        ExcelUtil<WakUserActivityRecord> util = new ExcelUtil<WakUserActivityRecord>(WakUserActivityRecord.class);
        util.exportExcel(response, list, "用户活动记录数据");
    }

    /**
     * 获取用户活动记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakUserActivityRecordService.selectWakUserActivityRecordById(id));
    }

    /**
     * 新增用户活动记录
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:add')")
    @Log(title = "用户活动记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakUserActivityRecord wakUserActivityRecord)
    {
        return toAjax(wakUserActivityRecordService.insertWakUserActivityRecord(wakUserActivityRecord));
    }

    /**
     * 修改用户活动记录
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:edit')")
    @Log(title = "用户活动记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakUserActivityRecord wakUserActivityRecord)
    {
        return toAjax(wakUserActivityRecordService.updateWakUserActivityRecord(wakUserActivityRecord));
    }

    /**
     * 删除用户活动记录
     */
    @PreAuthorize("@ss.hasPermi('wak:activityRecord:remove')")
    @Log(title = "用户活动记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakUserActivityRecordService.deleteWakUserActivityRecordByIds(ids));
    }
}
