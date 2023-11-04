package usts.pycro.pycslt.manager.order.service.impl;

import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.order.mapper.OrderInfoMapper;
import usts.pycro.pycslt.manager.order.service.OrderInfoService;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;

import static com.mybatisflex.core.query.QueryMethods.dateFormat;
import static com.mybatisflex.core.query.QueryMethods.sum;
import static usts.pycro.pycslt.model.entity.order.table.OrderInfoTableDef.ORDER_INFO;

/**
 * 订单 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    /**
     * 根据日期查询订单统计信息
     *
     * @param targetDate
     * @return
     */
    @Override
    public OrderStatistics selectStatisticsByDate(String targetDate) {
        return mapper.selectOneByQueryAs(QueryWrapper.create()
                        .select(
                                dateFormat(ORDER_INFO.CREATE_TIME, "%Y-%m-%d").as(OrderStatistics::getOrderDate),
                                sum(ORDER_INFO.TOTAL_AMOUNT).as(OrderStatistics::getTotalAmount),
                                QueryMethods.count(ORDER_INFO.ID).as(OrderStatistics::getTotalNum)
                        )
                        .from(ORDER_INFO)
                        .where(dateFormat(ORDER_INFO.CREATE_TIME, "%Y-%m-%d").eq(targetDate))
                        .groupBy(dateFormat(ORDER_INFO.CREATE_TIME, "%Y-%m-%d")),
                OrderStatistics.class);
    }
}
