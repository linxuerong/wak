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
import com.ruoyi.wak.domain.WakOutputlogReward;
import com.ruoyi.wak.service.IWakOutputlogRewardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 产出分润表Controller
 * 
 * @author ruoyi
 * @date 2024-02-23
 */
@RestController
@RequestMapping("/wak/outputlogReward")
public class WakOutputlogRewardController extends BaseController
{
    @Autowired
    private IWakOutputlogRewardService wakOutputlogRewardService;

    /**
     * 查询产出分润表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakOutputlogReward wakOutputlogReward)
    {
        startPage();
        List<WakOutputlogReward> list = wakOutputlogRewardService.selectWakOutputlogRewardList(wakOutputlogReward);
        return getDataTable(list);
    }

    /**
     * 导出产出分润表列表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:export')")
    @Log(title = "产出分润表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakOutputlogReward wakOutputlogReward)
    {
        List<WakOutputlogReward> list = wakOutputlogRewardService.selectWakOutputlogRewardList(wakOutputlogReward);
        ExcelUtil<WakOutputlogReward> util = new ExcelUtil<WakOutputlogReward>(WakOutputlogReward.class);
        util.exportExcel(response, list, "产出分润表数据");
    }

    /**
     * 获取产出分润表详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakOutputlogRewardService.selectWakOutputlogRewardById(id));
    }

    /**
     * 新增产出分润表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:add')")
    @Log(title = "产出分润表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakOutputlogReward wakOutputlogReward)
    {
        return toAjax(wakOutputlogRewardService.insertWakOutputlogReward(wakOutputlogReward));
    }

    /**
     * 修改产出分润表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:edit')")
    @Log(title = "产出分润表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakOutputlogReward wakOutputlogReward)
    {
        return toAjax(wakOutputlogRewardService.updateWakOutputlogReward(wakOutputlogReward));
    }

    /**
     * 删除产出分润表
     */
    @PreAuthorize("@ss.hasPermi('wak:outputlogReward:remove')")
    @Log(title = "产出分润表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakOutputlogRewardService.deleteWakOutputlogRewardByIds(ids));
    }
}
