package com.ruoyi.wak.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.domain.WakExchangelog;
import com.ruoyi.wak.service.IWakAddressService;
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
import com.ruoyi.wak.domain.WakOutputlog;
import com.ruoyi.wak.service.IWakOutputlogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户产出收益日志Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/outputlog")
public class WakOutputlogController extends BaseController
{
    @Autowired
    private IWakOutputlogService wakOutputlogService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询用户产出收益日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakOutputlog wakOutputlog)
    {
        if (StringUtils.isNotBlank(wakOutputlog.getNickname())){
            WakAuthaddress param = new WakAuthaddress();
            param.setNickname(wakOutputlog.getNickname());
            List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(param);
            if (wakAuthaddresses.size()>0){
                WakAuthaddress wakAuthaddress = wakAuthaddresses.get(0);
                wakOutputlog.setUid(wakAuthaddress.getId());
            }else {
                List<WakOutputlog> list = new ArrayList<>();
                return getDataTable(list);
            }
        }
        startPage();
        List<WakOutputlog> list = wakOutputlogService.selectWakOutputlogList(wakOutputlog);
        for (WakOutputlog wakOutputlog1:
        list) {
            WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakOutputlog1.getUid());
            if (wakAuthaddress==null){
                continue;
            }
            wakOutputlog1.setNickname(wakAuthaddress.getNickname());
        }
        return getDataTable(list);
    }

    /**
     * 导出用户产出收益日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:export')")
    @Log(title = "用户产出收益日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakOutputlog wakOutputlog)
    {
        List<WakOutputlog> list = wakOutputlogService.selectWakOutputlogList(wakOutputlog);
        ExcelUtil<WakOutputlog> util = new ExcelUtil<WakOutputlog>(WakOutputlog.class);
        util.exportExcel(response, list, "用户产出收益日志数据");
    }

    /**
     * 获取用户产出收益日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakOutputlogService.selectWakOutputlogById(id));
    }

    /**
     * 新增用户产出收益日志
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:add')")
    @Log(title = "用户产出收益日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakOutputlog wakOutputlog)
    {
        return toAjax(wakOutputlogService.insertWakOutputlog(wakOutputlog));
    }

    /**
     * 修改用户产出收益日志
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:edit')")
    @Log(title = "用户产出收益日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakOutputlog wakOutputlog)
    {
        return toAjax(wakOutputlogService.updateWakOutputlog(wakOutputlog));
    }

    /**
     * 删除用户产出收益日志
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlog:remove')")
    @Log(title = "用户产出收益日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakOutputlogService.deleteWakOutputlogByIds(ids));
    }
}
