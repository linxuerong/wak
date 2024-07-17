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
import com.ruoyi.wak.domain.WakTransferlog;
import com.ruoyi.wak.service.IWakTransferlogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 转账记录Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/transferlog")
public class WakTransferlogController extends BaseController
{
    @Autowired
    private IWakTransferlogService wakTransferlogService;

    /**
     * 查询转账记录列表
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakTransferlog wakTransferlog)
    {
        startPage();
        List<WakTransferlog> list = wakTransferlogService.selectWakTransferlogList(wakTransferlog);
        return getDataTable(list);
    }

    /**
     * 导出转账记录列表
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:export')")
    @Log(title = "转账记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakTransferlog wakTransferlog)
    {
        List<WakTransferlog> list = wakTransferlogService.selectWakTransferlogList(wakTransferlog);
        ExcelUtil<WakTransferlog> util = new ExcelUtil<WakTransferlog>(WakTransferlog.class);
        util.exportExcel(response, list, "转账记录数据");
    }

    /**
     * 获取转账记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakTransferlogService.selectWakTransferlogById(id));
    }

    /**
     * 新增转账记录
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:add')")
    @Log(title = "转账记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakTransferlog wakTransferlog)
    {
        return toAjax(wakTransferlogService.insertWakTransferlog(wakTransferlog));
    }

    /**
     * 修改转账记录
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:edit')")
    @Log(title = "转账记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakTransferlog wakTransferlog)
    {
        return toAjax(wakTransferlogService.updateWakTransferlog(wakTransferlog));
    }

    /**
     * 删除转账记录
     */
    @PreAuthorize("@ss.hasPermi('wak:transferlog:remove')")
    @Log(title = "转账记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakTransferlogService.deleteWakTransferlogByIds(ids));
    }
}
