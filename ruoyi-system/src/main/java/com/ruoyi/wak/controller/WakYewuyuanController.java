package com.ruoyi.wak.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.wak.vo.WakYewuyuanVo;
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
import com.ruoyi.wak.domain.WakYewuyuan;
import com.ruoyi.wak.service.IWakYewuyuanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务员Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/wak/yewuyuan")
public class WakYewuyuanController extends BaseController
{
    @Autowired
    private IWakYewuyuanService wakYewuyuanService;

    /**
     * 查询业务员列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakYewuyuan wakYewuyuan)
    {
        startPage();
        List<WakYewuyuanVo> list = wakYewuyuanService.selectWakYewuyuanList(wakYewuyuan);
        return getDataTable(list);
    }

    /**
     * 导出业务员列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:export')")
    @Log(title = "业务员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakYewuyuan wakYewuyuan)
    {
        List<WakYewuyuanVo> list = wakYewuyuanService.selectWakYewuyuanList(wakYewuyuan);
        ExcelUtil<WakYewuyuanVo> util = new ExcelUtil<WakYewuyuanVo>(WakYewuyuanVo.class);
        util.exportExcel(response, list, "业务员数据");
    }

    /**
     * 获取业务员详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakYewuyuanService.selectWakYewuyuanById(id));
    }

    /**
     * 新增业务员
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:add')")
    @Log(title = "业务员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakYewuyuan wakYewuyuan)
    {
        return toAjax(wakYewuyuanService.insertWakYewuyuan(wakYewuyuan));
    }

    /**
     * 修改业务员
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:edit')")
    @Log(title = "业务员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakYewuyuan wakYewuyuan)
    {
        return toAjax(wakYewuyuanService.updateWakYewuyuan(wakYewuyuan));
    }

    /**
     * 删除业务员
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuyuan:remove')")
    @Log(title = "业务员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakYewuyuanService.deleteWakYewuyuanByIds(ids));
    }
}
