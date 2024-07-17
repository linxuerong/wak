package com.ruoyi.web.controller.wak;

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
import com.ruoyi.system.domain.SysAuthenticator;
import com.ruoyi.system.service.ISysAuthenticatorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 谷歌验证Controller
 * 
 * @author ruoyi
 * @date 2023-12-21
 */
@RestController
@RequestMapping("/system/authenticator")
public class SysAuthenticatorController extends BaseController
{
    @Autowired
    private ISysAuthenticatorService sysAuthenticatorService;

    /**
     * 查询谷歌验证列表
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAuthenticator sysAuthenticator)
    {
        startPage();
        List<SysAuthenticator> list = sysAuthenticatorService.selectSysAuthenticatorList(sysAuthenticator);
        return getDataTable(list);
    }

    /**
     * 导出谷歌验证列表
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:export')")
    @Log(title = "谷歌验证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAuthenticator sysAuthenticator)
    {
        List<SysAuthenticator> list = sysAuthenticatorService.selectSysAuthenticatorList(sysAuthenticator);
        ExcelUtil<SysAuthenticator> util = new ExcelUtil<SysAuthenticator>(SysAuthenticator.class);
        util.exportExcel(response, list, "谷歌验证数据");
    }

    /**
     * 获取谷歌验证详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(sysAuthenticatorService.selectSysAuthenticatorByUserId(userId));
    }

    /**
     * 新增谷歌验证
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:add')")
    @Log(title = "谷歌验证", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAuthenticator sysAuthenticator)
    {
        return toAjax(sysAuthenticatorService.insertSysAuthenticator(sysAuthenticator));
    }

    /**
     * 修改谷歌验证
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:edit')")
    @Log(title = "谷歌验证", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAuthenticator sysAuthenticator)
    {
        return toAjax(sysAuthenticatorService.updateSysAuthenticator(sysAuthenticator));
    }

    /**
     * 删除谷歌验证
     */
    @PreAuthorize("@ss.hasPermi('system:authenticator:remove')")
    @Log(title = "谷歌验证", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(sysAuthenticatorService.deleteSysAuthenticatorByUserIds(userIds));
    }
}
