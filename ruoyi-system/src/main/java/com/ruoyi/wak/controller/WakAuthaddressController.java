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
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.service.IWakAuthaddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 授权列Controller
 *
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/authaddress")
public class WakAuthaddressController extends BaseController
{
    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询授权列列表
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakAuthaddress wakAuthaddress)
    {
        startPage();
        List<WakAuthaddress> list = wakAuthaddressService.selectWakAuthaddressList(wakAuthaddress);
        return getDataTable(list);
    }

    /**
     * 导出授权列列表
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:export')")
    @Log(title = "授权列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakAuthaddress wakAuthaddress)
    {
        List<WakAuthaddress> list = wakAuthaddressService.selectWakAuthaddressList(wakAuthaddress);
        ExcelUtil<WakAuthaddress> util = new ExcelUtil<WakAuthaddress>(WakAuthaddress.class);
        util.exportExcel(response, list, "授权列数据");
    }

    /**
     * 获取授权列详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakAuthaddressService.selectWakAuthaddressById(id));
    }

    /**
     * 新增授权列
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:add')")
    @Log(title = "授权列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakAuthaddress wakAuthaddress)
    {
        return toAjax(wakAuthaddressService.insertWakAuthaddress(wakAuthaddress));
    }

    /**
     * 修改授权列
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:edit')")
    @Log(title = "授权列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakAuthaddress wakAuthaddress)
    {
        return toAjax(wakAuthaddressService.updateWakAuthaddress2(wakAuthaddress));
    }

    /**
     * 删除授权列
     */
    @PreAuthorize("@ss.hasPermi('wak:authaddress:remove')")
    @Log(title = "授权列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakAuthaddressService.deleteWakAuthaddressByIds(ids));
    }
}
