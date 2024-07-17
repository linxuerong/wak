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
import com.ruoyi.wak.domain.WakTongji;
import com.ruoyi.wak.service.IWakTongjiService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 统计Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/wak/tongji")
public class WakTongjiController extends BaseController
{
    @Autowired
    private IWakTongjiService wakTongjiService;

    /**
     * 查询统计列表
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakTongji wakTongji)
    {
        startPage();
        List<WakTongji> list = wakTongjiService.selectWakTongjiList(wakTongji);
        return getDataTable(list);
    }

    /**
     * 导出统计列表
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:export')")
    @Log(title = "统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakTongji wakTongji)
    {
        List<WakTongji> list = wakTongjiService.selectWakTongjiList(wakTongji);
        ExcelUtil<WakTongji> util = new ExcelUtil<WakTongji>(WakTongji.class);
        util.exportExcel(response, list, "统计数据");
    }

    /**
     * 获取统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakTongjiService.selectWakTongjiById(id));
    }

    /**
     * 新增统计
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:add')")
    @Log(title = "统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakTongji wakTongji)
    {
        return toAjax(wakTongjiService.insertWakTongji(wakTongji));
    }

    /**
     * 修改统计
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:edit')")
    @Log(title = "统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakTongji wakTongji)
    {
        return toAjax(wakTongjiService.updateWakTongji(wakTongji));
    }

    /**
     * 删除统计
     */
    @PreAuthorize("@ss.hasPermi('wak:tongji:remove')")
    @Log(title = "统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakTongjiService.deleteWakTongjiByIds(ids));
    }
}
