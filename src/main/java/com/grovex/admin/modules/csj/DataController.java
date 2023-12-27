package com.grovex.admin.modules.csj;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grovex.admin.modules.csj.util.DataCatch;
import com.grovex.admin.common.ditc.RoleDict;
import com.grovex.admin.common.utils.Result;
import com.grovex.admin.modules.csj.entity.CsjData;
import com.grovex.admin.modules.csj.service.CsjDataService;
import com.grovex.admin.modules.csj.vo.CsjQueryListVo;
import com.grovex.admin.modules.sys.vo.CatchDataVo;
import com.grovex.admin.modules.sys.vo.JwtVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/csj/data")
@RestController
@Api(tags = "穿山甲数据")
public class DataController {

    @Autowired
    private DataCatch dataCatch;

    @Autowired
    private CsjDataService csjDataService;

    public static final String TOKEN = "b6824a6b994baca9681e9c6d44b9d0064cdf12fed7f7f30e5dc930331318cade";


    @ApiOperation(value = "抓取数据")
    @PostMapping("/catch")
    public Result catchData(@RequestBody CatchDataVo catchDataVo, @SessionAttribute("jwt")JwtVo jwtVo) {
        if (!RoleDict.SUPPER_ADMIN.equals(jwtVo.getRoleType())){
            return Result.error().message("非超级管理员");
        }
        dataCatch.getCsjDateDaily(catchDataVo.getStartDate(), catchDataVo.getEndDate(), TOKEN);
        return Result.ok();
    }

    @ApiOperation(value = "获取表格数据")
        @PostMapping("/list")
    public Result list(@RequestBody CsjQueryListVo csjQueryListVo, @SessionAttribute("jwt")JwtVo jwtVo) {
        if (!RoleDict.SUPPER_ADMIN.equals(jwtVo.getRoleType())){
            return Result.error().message("非超级管理员");
        }
        QueryWrapper<CsjData> queryWrapper = new QueryWrapper<>();
        String adSlotId = csjQueryListVo.getAdSlotId();
        if ( adSlotId != null && !"".equals(adSlotId)){
            queryWrapper.like("ad_slot_id", adSlotId);
        }
        String date = csjQueryListVo.getDate();
        if ( date != null && !"".equals(date)){
            queryWrapper.eq("date", date);
        }
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem().setAsc(false).setColumn("date"));
        Page<CsjData> page = csjDataService.page(
                new Page<CsjData>()
                        .addOrder(orderItems)
                        .setSize(csjQueryListVo.getSize())
                        .setCurrent(csjQueryListVo.getCurrent())
                ,queryWrapper);
        return Result.ok().data("page",page);
    }
}
