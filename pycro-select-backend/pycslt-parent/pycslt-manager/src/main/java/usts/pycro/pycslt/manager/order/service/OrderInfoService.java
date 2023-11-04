package usts.pycro.pycslt.manager.order.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;

/**
 * 订单 服务层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 根据日期查询订单统计信息
     * @param targetDate
     * @return
     */
    OrderStatistics selectStatisticsByDate(String targetDate);

}
