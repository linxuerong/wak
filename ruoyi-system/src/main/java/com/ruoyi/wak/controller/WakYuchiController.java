package com.ruoyi.wak.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.service.IWakAuthaddressService;
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
import com.ruoyi.wak.domain.WakYuchi;
import com.ruoyi.wak.service.IWakYuchiService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 鱼池Controller
 *
 * @author ruoyi
 * @date 2025-01-24
 */
@RestController
@RequestMapping("/wak/yuchi")
public class WakYuchiController extends BaseController
{
    @Autowired
    private IWakYuchiService wakYuchiService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询鱼池列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakYuchi wakYuchi) throws Exception {
        startPage();
        List<WakYuchi> list = wakYuchiService.selectWakYuchiList(wakYuchi);
        return getDataTable(list);
    }

    /**
     * 导出鱼池列表
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:export')")
    @Log(title = "鱼池", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakYuchi wakYuchi) throws Exception {
        List<WakYuchi> list = wakYuchiService.selectWakYuchiList(wakYuchi);
        ExcelUtil<WakYuchi> util = new ExcelUtil<WakYuchi>(WakYuchi.class);
        util.exportExcel(response, list, "鱼池数据");
    }

    /**
     * 获取鱼池详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(wakYuchiService.selectWakYuchiByUserId(userId));
    }

    /**
     * 新增鱼池
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:add')")
    @Log(title = "鱼池", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakYuchi wakYuchi)
    {
        WakAuthaddress authaddress = wakAuthaddressService.selectWakAuthaddressByAddress(wakYuchi.getAddress());
        if (authaddress == null) {
            return AjaxResult.error("该地址不存在");
        }
        wakYuchi.setUserId(authaddress.getId());
        return toAjax(wakYuchiService.insertWakYuchi(wakYuchi));
    }

    /**
     * 修改鱼池
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:edit')")
    @Log(title = "鱼池", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakYuchi wakYuchi)
    {
        return toAjax(wakYuchiService.updateWakYuchi(wakYuchi));
    }

    /**
     * 删除鱼池
     */
    @PreAuthorize("@ss.hasPermi('wak:yuchi:remove')")
    @Log(title = "鱼池", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(wakYuchiService.deleteWakYuchiByUserIds(userIds));
    }
}
