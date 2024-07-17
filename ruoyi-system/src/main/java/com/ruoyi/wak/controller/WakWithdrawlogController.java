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
import com.ruoyi.wak.domain.WakWithdrawlog;
import com.ruoyi.wak.service.IWakWithdrawlogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户提现日志Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/withdrawlog")
public class WakWithdrawlogController extends BaseController
{
    @Autowired
    private IWakWithdrawlogService wakWithdrawlogService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询用户提现日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakWithdrawlog wakWithdrawlog)
    {
        startPage();
        List<WakWithdrawlog> list = wakWithdrawlogService.selectWakWithdrawlogList(wakWithdrawlog);
        for (WakWithdrawlog wakWithdrawlog1:
        list) {
            WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakWithdrawlog1.getUid());
            if (wakAuthaddress==null){
                continue;
            }
            wakWithdrawlog1.setSystemUsdt(wakAuthaddress.getSystemUsdt());
        }
        return getDataTable(list);
    }

    /**
     * 导出用户提现日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:export')")
    @Log(title = "用户提现日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakWithdrawlog wakWithdrawlog)
    {
        List<WakWithdrawlog> list = wakWithdrawlogService.selectWakWithdrawlogList(wakWithdrawlog);
        ExcelUtil<WakWithdrawlog> util = new ExcelUtil<WakWithdrawlog>(WakWithdrawlog.class);
        util.exportExcel(response, list, "用户提现日志数据");
    }

    /**
     * 获取用户提现日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakWithdrawlogService.selectWakWithdrawlogById(id));
    }

    /**
     * 新增用户提现日志
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:add')")
    @Log(title = "用户提现日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakWithdrawlog wakWithdrawlog)
    {
        return toAjax(wakWithdrawlogService.insertWakWithdrawlog(wakWithdrawlog));
    }

    /**
     * 修改用户提现日志
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:edit')")
    @Log(title = "用户提现日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakWithdrawlog wakWithdrawlog)
    {
        WakWithdrawlog check = wakWithdrawlogService.selectWakWithdrawlogById(wakWithdrawlog.getId());
        if (check.getStatus()!=0){
            wakWithdrawlog.setStatus(null);
        }

        return toAjax(wakWithdrawlogService.updateWakWithdrawlog(wakWithdrawlog));
    }

    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:edit')")
    @Log(title = "用户提现日志", businessType = BusinessType.UPDATE)
    @PostMapping("pass")
    public AjaxResult pass(@RequestBody WakWithdrawlog wakWithdrawlog)
    {
        if (wakWithdrawlog.getStatus()!=1){
            return AjaxResult.error("error");
        }
        WakWithdrawlog check = wakWithdrawlogService.selectWakWithdrawlogById(wakWithdrawlog.getId());
        if (check.getStatus()!=0){
            wakWithdrawlog.setStatus(null);
        }

        return toAjax(wakWithdrawlogService.pass(wakWithdrawlog));
    }

    /**
     * 删除用户提现日志
     */
    @PreAuthorize("@ss.hasPermi('wak:withdrawlog:remove')")
    @Log(title = "用户提现日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakWithdrawlogService.deleteWakWithdrawlogByIds(ids));
    }
}
