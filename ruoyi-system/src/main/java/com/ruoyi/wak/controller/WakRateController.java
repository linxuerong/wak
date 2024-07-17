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
import com.ruoyi.wak.domain.WakRate;
import com.ruoyi.wak.service.IWakRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收益比例设置Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/wak/rate")
public class WakRateController extends BaseController
{
    @Autowired
    private IWakRateService wakRateService;

    /**
     * 查询收益比例设置列表
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakRate wakRate)
    {
        startPage();
        List<WakRate> list = wakRateService.selectWakRateList(wakRate);
        return getDataTable(list);
    }

    /**
     * 导出收益比例设置列表
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:export')")
    @Log(title = "收益比例设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakRate wakRate)
    {
        List<WakRate> list = wakRateService.selectWakRateList(wakRate);
        ExcelUtil<WakRate> util = new ExcelUtil<WakRate>(WakRate.class);
        util.exportExcel(response, list, "收益比例设置数据");
    }

    /**
     * 获取收益比例设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakRateService.selectWakRateById(id));
    }

    /**
     * 新增收益比例设置
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:add')")
    @Log(title = "收益比例设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakRate wakRate)
    {
        return toAjax(wakRateService.insertWakRate(wakRate));
    }

    /**
     * 修改收益比例设置
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:edit')")
    @Log(title = "收益比例设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakRate wakRate)
    {
        return toAjax(wakRateService.updateWakRate(wakRate));
    }

    /**
     * 删除收益比例设置
     */
    @PreAuthorize("@ss.hasPermi('wak:rate:remove')")
    @Log(title = "收益比例设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakRateService.deleteWakRateByIds(ids));
    }
}
