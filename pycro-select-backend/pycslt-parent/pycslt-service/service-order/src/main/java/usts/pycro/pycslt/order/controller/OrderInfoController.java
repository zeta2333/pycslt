package usts.pycro.pycslt.order.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.model.bo.h5.OrderInfoBo;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.model.vo.h5.TradeVo;
import usts.pycro.pycslt.order.service.OrderInfoService;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 11:04
 */
@Tag(name = "订单管理")
@RestController
@RequestMapping(value = "/api/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    /**
     * 获取订单分页列表
     *
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
        return Result.ok(pageInfo);
    }

    /**
     * 立即购买
     *
     * @param skuId
     * @return
     */
    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.ok(tradeVo);
    }


    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.ok(orderInfo);
    }

    /**
     * 提交订单
     *
     * @param orderInfoBo
     * @return
     */
    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderInfoBo orderInfoBo) {
        Long orderId = orderInfoService.submitOrder(orderInfoBo);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    /**
     * 获取下单信息
     *
     * @return
     */
    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.ok(tradeVo);
    }
}
