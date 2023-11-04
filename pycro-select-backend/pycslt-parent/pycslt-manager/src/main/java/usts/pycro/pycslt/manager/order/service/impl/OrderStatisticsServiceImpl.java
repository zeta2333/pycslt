package usts.pycro.pycslt.manager.order.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.order.mapper.OrderStatisticsMapper;
import usts.pycro.pycslt.manager.order.service.OrderStatisticsService;
import usts.pycro.pycslt.model.dto.order.OrderStatisticsBo;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;
import usts.pycro.pycslt.model.vo.order.OrderStatisticsVo;

import java.math.BigDecimal;
import java.util.List;

import static usts.pycro.pycslt.model.entity.order.table.OrderStatisticsTableDef.ORDER_STATISTICS;

/**
 * 订单统计 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@Service
public class OrderStatisticsServiceImpl extends ServiceImpl<OrderStatisticsMapper, OrderStatistics> implements OrderStatisticsService {

    /**
     * 查询统计数据
     *
     * @param orderStatisticsBo
     * @return
     */
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsBo orderStatisticsBo) {
        // 根据条件查询
        List<OrderStatistics> orderStatisticsList = list(QueryWrapper.create()
                .select(ORDER_STATISTICS.ORDER_DATE, ORDER_STATISTICS.TOTAL_AMOUNT)
                .where(ORDER_STATISTICS.ORDER_DATE.ge(orderStatisticsBo.getCreateTimeBegin()))
                .and(ORDER_STATISTICS.ORDER_DATE.le(orderStatisticsBo.getCreateTimeEnd()))
                .orderBy(ORDER_STATISTICS.ORDER_DATE, true));

        // 构建dateList和amountList
        List<String> dateList = orderStatisticsList.stream()
                .map(e -> DateUtil.format(e.getOrderDate(), "yyyy-MM-dd"))
                .toList();
        List<BigDecimal> amountList = orderStatisticsList.stream()
                .map(OrderStatistics::getTotalAmount)
                .toList();

        // 封装到vo中
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);

        return orderStatisticsVo;
    }
}
