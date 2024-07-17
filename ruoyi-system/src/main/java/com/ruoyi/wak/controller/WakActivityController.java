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
import com.ruoyi.wak.domain.WakActivity;
import com.ruoyi.wak.service.IWakActivityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动表Controller
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/wak/activity")
public class WakActivityController extends BaseController
{
    @Autowired
    private IWakActivityService wakActivityService;

    /**
     * 查询活动表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakActivity wakActivity)
    {
        startPage();
        List<WakActivity> list = wakActivityService.selectWakActivityList(wakActivity);
        return getDataTable(list);
    }

    /**
     * 导出活动表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:export')")
    @Log(title = "活动表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakActivity wakActivity)
    {
        List<WakActivity> list = wakActivityService.selectWakActivityList(wakActivity);
        ExcelUtil<WakActivity> util = new ExcelUtil<WakActivity>(WakActivity.class);
        util.exportExcel(response, list, "活动表数据");
    }

    /**
     * 获取活动表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakActivityService.selectWakActivityById(id));
    }

    /**
     * 新增活动表
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:add')")
    @Log(title = "活动表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakActivity wakActivity)
    {
        return toAjax(wakActivityService.insertWakActivity(wakActivity));
    }

    /**
     * 修改活动表
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:edit')")
    @Log(title = "活动表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakActivity wakActivity)
    {
        return toAjax(wakActivityService.updateWakActivity(wakActivity));
    }

    /**
     * 删除活动表
     */
    @PreAuthorize("@ss.hasPermi('wak:activity:remove')")
    @Log(title = "活动表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakActivityService.deleteWakActivityByIds(ids));
    }
}
