package com.ruoyi.wak.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.wak.domain.WakAddress;
import com.ruoyi.wak.domain.WakAuthaddress;
import com.ruoyi.wak.domain.WakExchangelog;
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
import com.ruoyi.wak.domain.WakFzOrder;
import com.ruoyi.wak.service.IWakFzOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质押订单Controller
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
@RestController
@RequestMapping("/wak/fzOrder")
public class WakFzOrderController extends BaseController
{
    @Autowired
    private IWakFzOrderService wakFzOrderService;

    @Autowired
    private IWakAuthaddressService wakAuthaddressService;

    /**
     * 查询质押订单列表
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(WakFzOrder wakFzOrder)
    {
        if (StringUtils.isNotBlank(wakFzOrder.getNickname())){
            WakAuthaddress param = new WakAuthaddress();
            param.setNickname(wakFzOrder.getNickname());
            List<WakAuthaddress> wakAuthaddresses = wakAuthaddressService.selectWakAuthaddressList(param);
            if (wakAuthaddresses.size()>0){
                WakAuthaddress wakAuthaddress = wakAuthaddresses.get(0);
                wakFzOrder.setUid(wakAuthaddress.getId());
            }else {
                List<WakFzOrder> list = new ArrayList<>();
                return getDataTable(list);
            }
        }
        startPage();
        List<WakFzOrder> list = wakFzOrderService.selectWakFzOrderList(wakFzOrder);
        for (WakFzOrder wakFzOrder1:
        list) {
            WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakFzOrder1.getUid());
            if (wakAuthaddress==null){
                continue;
            }
            wakFzOrder1.setNickname(wakAuthaddress.getNickname());
        }
        return getDataTable(list);
    }

    /**
     * 导出质押订单列表
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:export')")
    @Log(title = "质押订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WakFzOrder wakFzOrder)
    {
        List<WakFzOrder> list = wakFzOrderService.selectWakFzOrderList(wakFzOrder);
        ExcelUtil<WakFzOrder> util = new ExcelUtil<WakFzOrder>(WakFzOrder.class);
        util.exportExcel(response, list, "质押订单数据");
    }

    /**
     * 获取质押订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(wakFzOrderService.selectWakFzOrderById(id));
    }

    /**
     * 新增质押订单
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:add')")
    @Log(title = "质押订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WakFzOrder wakFzOrder)
    {
        //查找用户
        WakAuthaddress wakAuthaddress = wakAuthaddressService.selectWakAuthaddressById(wakFzOrder.getUid());
        if (wakAuthaddress==null){
            return AjaxResult.error("用户不存在");
        }
        wakFzOrder.setAddress(wakAuthaddress.getAddress());
        //验证用户余额是否足够
        if (wakFzOrder.getFzUsdt().compareTo(wakAuthaddress.getSystemUsdt())>0){
            return AjaxResult.error("用户余额不足");
        }
        //验证质押要求是否大于质押usdt
        if (wakFzOrder.getFzUsdtLimit().compareTo(wakFzOrder.getFzUsdt())>0){
            return AjaxResult.error("输入的质押usdt，未达到质押要求");
        }
        return toAjax(wakFzOrderService.insertWakFzOrder(wakFzOrder));
    }

    /**
     * 修改质押订单
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:edit')")
    @Log(title = "质押订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WakFzOrder wakFzOrder)
    {
        return toAjax(wakFzOrderService.updateWakFzOrder(wakFzOrder));
    }

    /**
     * 删除质押订单
     */
    @PreAuthorize("@ss.hasPermi('wak:fzOrder:remove')")
    @Log(title = "质押订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(wakFzOrderService.deleteWakFzOrderByIds(ids));
    }
}
