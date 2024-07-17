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
import com.ruoyi.wak.domain.WakUserRecord;
import com.ruoyi.wak.service.IWakUserRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户操作记录表Controller
 * 
 * @author ruoyi
 * @date 2024-06-04
 */
@RestController
@RequestMapping("/wak/userRecord")
public class WakUserRecordController extends BaseController
{
    @Autowired
    private IWakUserRecordService wakUserRecordService;

    /**
     * 查询用户操作记录表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakUserRecord wakUserRecord)
    {
        startPage();
        List<WakUserRecord> list = wakUserRecordService.selectWakUserRecordList(wakUserRecord);
        return getDataTable(list);
    }

    /**
     * 导出用户操作记录表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:export')")
    @Log(title = "用户操作记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakUserRecord wakUserRecord)
    {
        List<WakUserRecord> list = wakUserRecordService.selectWakUserRecordList(wakUserRecord);
        ExcelUtil<WakUserRecord> util = new ExcelUtil<WakUserRecord>(WakUserRecord.class);
        util.exportExcel(response, list, "用户操作记录表数据");
    }

    /**
     * 获取用户操作记录表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakUserRecordService.selectWakUserRecordById(id));
    }

    /**
     * 新增用户操作记录表
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:add')")
    @Log(title = "用户操作记录表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakUserRecord wakUserRecord)
    {
        return toAjax(wakUserRecordService.insertWakUserRecord(wakUserRecord));
    }

    /**
     * 修改用户操作记录表
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:edit')")
    @Log(title = "用户操作记录表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakUserRecord wakUserRecord)
    {
        return toAjax(wakUserRecordService.updateWakUserRecord(wakUserRecord));
    }

    /**
     * 删除用户操作记录表
     */
    @PreAuthorize("@ss.hasPermi('wak:userRecord:remove')")
    @Log(title = "用户操作记录表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakUserRecordService.deleteWakUserRecordByIds(ids));
    }
}
