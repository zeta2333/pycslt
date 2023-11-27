package usts.pycro.pycslt.order.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.order.OrderInfo;

/**
 * 订单 映射层。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
