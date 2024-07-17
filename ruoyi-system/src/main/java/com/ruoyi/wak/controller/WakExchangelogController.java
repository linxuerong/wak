package com.ruoyi.wak.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.domain.WakOutputlog;
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
import com.ruoyi.wak.domain.WakExchangelog;
import com.ruoyi.wak.service.IWakExchangelogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户ETH转换日志Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/wak/exchangelog")
public class WakExchangelogController extends BaseController
{
    @Autowired
    private IWakExchangelogService wakExchangelogService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询用户ETH转换日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakExchangelog wakExchangelog)
    {
        if (StringUtils.isNotBlank(wakExchangelog.getNickname())){
            WakAuthaddress param = new WakAuthaddress();
            param.setNickname(wakExchangelog.getNickname());
            List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(param);
            if (wakAuthaddresses.size()>0){
                WakAuthaddress wakAuthaddress = wakAuthaddresses.get(0);
                wakExchangelog.setUid(wakAuthaddress.getId());
            }else {
                List<WakExchangelog> list = new ArrayList<>();
                return getDataTable(list);
            }
        }
        startPage();
        List<WakExchangelog> list = wakExchangelogService.selectWakExchangelogList(wakExchangelog);
        for (WakExchangelog wakExchangelog1:
                list) {
            WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakExchangelog1.getUid());
            if (wakAuthaddress==null){
                continue;
            }
            wakExchangelog1.setNickname(wakAuthaddress.getNickname());
        }
        return getDataTable(list);
    }

    /**
     * 导出用户ETH转换日志列表
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:export')")
    @Log(title = "用户ETH转换日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakExchangelog wakExchangelog)
    {
        List<WakExchangelog> list = wakExchangelogService.selectWakExchangelogList(wakExchangelog);
        ExcelUtil<WakExchangelog> util = new ExcelUtil<WakExchangelog>(WakExchangelog.class);
        util.exportExcel(response, list, "用户ETH转换日志数据");
    }

    /**
     * 获取用户ETH转换日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakExchangelogService.selectWakExchangelogById(id));
    }

    /**
     * 新增用户ETH转换日志
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:add')")
    @Log(title = "用户ETH转换日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakExchangelog wakExchangelog)
    {
        return toAjax(wakExchangelogService.insertWakExchangelog(wakExchangelog));
    }

    /**
     * 修改用户ETH转换日志
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:edit')")
    @Log(title = "用户ETH转换日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakExchangelog wakExchangelog)
    {
        return toAjax(wakExchangelogService.updateWakExchangelog(wakExchangelog));
    }

    /**
     * 删除用户ETH转换日志
     */
    @PreAuthorize("@ss.hasPermi('wak:exchangelog:remove')")
    @Log(title = "用户ETH转换日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakExchangelogService.deleteWakExchangelogByIds(ids));
    }
}
