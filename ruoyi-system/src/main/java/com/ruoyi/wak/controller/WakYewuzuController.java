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
import com.ruoyi.wak.domain.WakYewuzu;
import com.ruoyi.wak.service.IWakYewuzuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务组Controller
 * 
 * @author ruoyi
 * @date 2023-12-12
 */
@RestController
@RequestMapping("/wak/yewuzu")
public class WakYewuzuController extends BaseController
{
    @Autowired
    private IWakYewuzuService wakYewuzuService;

    /**
     * 查询业务组列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakYewuzu wakYewuzu)
    {
        startPage();
        List<WakYewuzu> list = wakYewuzuService.selectWakYewuzuList(wakYewuzu);
        return getDataTable(list);
    }

    /**
     * 导出业务组列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:export')")
    @Log(title = "业务组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakYewuzu wakYewuzu)
    {
        List<WakYewuzu> list = wakYewuzuService.selectWakYewuzuList(wakYewuzu);
        ExcelUtil<WakYewuzu> util = new ExcelUtil<WakYewuzu>(WakYewuzu.class);
        util.exportExcel(response, list, "业务组数据");
    }

    /**
     * 获取业务组详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakYewuzuService.selectWakYewuzuById(id));
    }

    /**
     * 新增业务组
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:add')")
    @Log(title = "业务组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakYewuzu wakYewuzu)
    {
        return toAjax(wakYewuzuService.insertWakYewuzu(wakYewuzu));
    }

    /**
     * 修改业务组
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:edit')")
    @Log(title = "业务组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakYewuzu wakYewuzu)
    {
        return toAjax(wakYewuzuService.updateWakYewuzu(wakYewuzu));
    }

    /**
     * 删除业务组
     */
    @PreAuthorize("@ss.hasPermi('wak:yewuzu:remove')")
    @Log(title = "业务组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakYewuzuService.deleteWakYewuzuByIds(ids));
    }
}
