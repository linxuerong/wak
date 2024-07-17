package com.ruoyi.web.controller.wak;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.redis.RedisCache;
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
import com.ruoyi.wak.domain.WakAddress;
import com.ruoyi.wak.service.IWakAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 控制地址Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/wak")
public class WakAddressController extends BaseController
{
    @Autowired
    private IWakAddressService wakAddressService;


    /**
     * 查询控制地址列表
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakAddress wakAddress)
    {
        startPage();
        List<WakAddress> list = wakAddressService.selectWakAddressList(wakAddress);
        return getDataTable(list);
    }

    /**
     * 导出控制地址列表
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:export')")
    @Log(title = "控制地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakAddress wakAddress)
    {
        List<WakAddress> list = wakAddressService.selectWakAddressList(wakAddress);
        ExcelUtil<WakAddress> util = new ExcelUtil<WakAddress>(WakAddress.class);
        util.exportExcel(response, list, "控制地址数据");
    }

    /**
     * 获取控制地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakAddressService.selectWakAddressById(id));
    }

    /**
     * 新增控制地址
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:add')")
    @Log(title = "控制地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakAddress wakAddress)
    {
        return toAjax(wakAddressService.insertWakAddress(wakAddress));
    }

    /**
     * 修改控制地址
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:edit')")
    @Log(title = "控制地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakAddress wakAddress)
    {
        return toAjax(wakAddressService.updateWakAddress(wakAddress));
    }

    /**
     * 删除控制地址
     */
    @PreAuthorize("@ss.hasPermi('wak:wak:remove')")
    @Log(title = "控制地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakAddressService.deleteWakAddressByIds(ids));
    }
}
