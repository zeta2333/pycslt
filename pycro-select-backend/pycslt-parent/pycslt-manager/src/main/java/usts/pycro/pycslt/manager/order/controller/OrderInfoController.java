package usts.pycro.pycslt.manager.order.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.order.service.OrderInfoService;
import usts.pycro.pycslt.manager.order.service.OrderStatisticsService;
import usts.pycro.pycslt.model.bo.order.OrderStatisticsBo;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.order.OrderStatisticsVo;

import java.io.Serializable;
import java.util.List;

/**
 * 订单 控制层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderStatisticsService orderStatisticsService;


    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsBo orderStatisticsBo) {
        OrderStatisticsVo orderStatisticsVo = orderStatisticsService.getOrderStatisticsData(orderStatisticsBo);
        return Result.ok(orderStatisticsVo);
    }

    /**
     * 添加订单。
     *
     * @param orderInfo 订单
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.save(orderInfo);
    }

    /**
     * 根据主键删除订单。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return orderInfoService.removeById(id);
    }

    /**
     * 根据主键更新订单。
     *
     * @param orderInfo 订单
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.updateById(orderInfo);
    }

    /**
     * 查询所有订单。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<OrderInfo> list() {
        return orderInfoService.list();
    }

    /**
     * 根据订单主键获取详细信息。
     *
     * @param id 订单主键
     * @return 订单详情
     */
    @GetMapping("getInfo/{id}")
    public OrderInfo getInfo(@PathVariable Serializable id) {
        return orderInfoService.getById(id);
    }

    /**
     * 分页查询订单。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<OrderInfo> page(Page<OrderInfo> page) {
        return orderInfoService.page(page);
    }

}
