package usts.pycro.pycslt.manager.order.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.order.OrderStatisticsBo;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;
import usts.pycro.pycslt.model.vo.order.OrderStatisticsVo;

/**
 * 订单统计 服务层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
public interface OrderStatisticsService extends IService<OrderStatistics> {

    /**
     * 查询统计数据
     *
     * @param orderStatisticsBo
     * @return
     */
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsBo orderStatisticsBo);
}
