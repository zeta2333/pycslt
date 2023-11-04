package usts.pycro.pycslt.manager.order.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.order.service.OrderStatisticsService;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;

import java.io.Serializable;
import java.util.List;

/**
 * 订单统计 控制层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@RestController
@RequestMapping("/admin/order/orderStatistics")
public class OrderStatisticsController {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    /**
     * 添加订单统计。
     *
     * @param orderStatistics 订单统计
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody OrderStatistics orderStatistics) {
        return orderStatisticsService.save(orderStatistics);
    }

    /**
     * 根据主键删除订单统计。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return orderStatisticsService.removeById(id);
    }

    /**
     * 根据主键更新订单统计。
     *
     * @param orderStatistics 订单统计
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody OrderStatistics orderStatistics) {
        return orderStatisticsService.updateById(orderStatistics);
    }

    /**
     * 查询所有订单统计。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<OrderStatistics> list() {
        return orderStatisticsService.list();
    }

    /**
     * 根据订单统计主键获取详细信息。
     *
     * @param id 订单统计主键
     * @return 订单统计详情
     */
    @GetMapping("getInfo/{id}")
    public OrderStatistics getInfo(@PathVariable Serializable id) {
        return orderStatisticsService.getById(id);
    }

    /**
     * 分页查询订单统计。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<OrderStatistics> page(Page<OrderStatistics> page) {
        return orderStatisticsService.page(page);
    }

}
