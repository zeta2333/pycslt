package usts.pycro.pycslt.manager.order.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.order.OrderStatistics;

/**
 * 订单统计 映射层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@Mapper
public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics> {

}
