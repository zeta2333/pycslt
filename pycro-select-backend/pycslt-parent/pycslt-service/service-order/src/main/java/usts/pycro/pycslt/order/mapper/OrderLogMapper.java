package usts.pycro.pycslt.order.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.order.OrderLog;

/**
 * 订单操作日志记录 映射层。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLog> {

}
