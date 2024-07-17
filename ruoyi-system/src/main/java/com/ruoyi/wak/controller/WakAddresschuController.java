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
import com.ruoyi.wak.domain.WakAddresschu;
import com.ruoyi.wak.service.IWakAddresschuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出款地址管理Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/addresschu")
public class WakAddresschuController extends BaseController
{
    @Autowired
    private IWakAddresschuService wakAddresschuService;

    /**
     * 查询出款地址管理列表
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakAddresschu wakAddresschu)
    {
        startPage();
        List<WakAddresschu> list = wakAddresschuService.selectWakAddresschuList(wakAddresschu);
        return getDataTable(list);
    }

    /**
     * 导出出款地址管理列表
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:export')")
    @Log(title = "出款地址管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakAddresschu wakAddresschu)
    {
        List<WakAddresschu> list = wakAddresschuService.selectWakAddresschuList(wakAddresschu);
        ExcelUtil<WakAddresschu> util = new ExcelUtil<WakAddresschu>(WakAddresschu.class);
        util.exportExcel(response, list, "出款地址管理数据");
    }

    /**
     * 获取出款地址管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakAddresschuService.selectWakAddresschuById(id));
    }

    /**
     * 新增出款地址管理
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:add')")
    @Log(title = "出款地址管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakAddresschu wakAddresschu)
    {
        return toAjax(wakAddresschuService.insertWakAddresschu(wakAddresschu));
    }

    /**
     * 修改出款地址管理
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:edit')")
    @Log(title = "出款地址管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakAddresschu wakAddresschu)
    {
        return toAjax(wakAddresschuService.updateWakAddresschu(wakAddresschu));
    }

    /**
     * 删除出款地址管理
     */
    @PreAuthorize("@ss.hasPermi('wak:addresschu:remove')")
    @Log(title = "出款地址管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakAddresschuService.deleteWakAddresschuByIds(ids));
    }
}
