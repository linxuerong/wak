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
import com.ruoyi.wak.domain.WakConfig;
import com.ruoyi.wak.service.IWakConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 平台配置Controller
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@RestController
@RequestMapping("/wak/config")
public class WakConfigController extends BaseController
{
    @Autowired
    private IWakConfigService wakConfigService;

    /**
     * 查询平台配置列表
     */
    @PreAuthorize("@ss.hasPermi('wak:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakConfig wakConfig)
    {
        startPage();
        List<WakConfig> list = wakConfigService.selectWakConfigList(wakConfig);
        return getDataTable(list);
    }

    /**
     * 导出平台配置列表
     */
    @PreAuthorize("@ss.hasPermi('wak:config:export')")
    @Log(title = "平台配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakConfig wakConfig)
    {
        List<WakConfig> list = wakConfigService.selectWakConfigList(wakConfig);
        ExcelUtil<WakConfig> util = new ExcelUtil<WakConfig>(WakConfig.class);
        util.exportExcel(response, list, "平台配置数据");
    }

    /**
     * 获取平台配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(wakConfigService.selectWakConfigById(id));
    }

    /**
     * 新增平台配置
     */
    @PreAuthorize("@ss.hasPermi('wak:config:add')")
    @Log(title = "平台配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakConfig wakConfig)
    {
        return toAjax(wakConfigService.insertWakConfig(wakConfig));
    }

    /**
     * 修改平台配置
     */
    @PreAuthorize("@ss.hasPermi('wak:config:edit')")
    @Log(title = "平台配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakConfig wakConfig)
    {
        return toAjax(wakConfigService.updateWakConfig(wakConfig));
    }

    /**
     * 删除平台配置
     */
    @PreAuthorize("@ss.hasPermi('wak:config:remove')")
    @Log(title = "平台配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(wakConfigService.deleteWakConfigByIds(ids));
    }
}
