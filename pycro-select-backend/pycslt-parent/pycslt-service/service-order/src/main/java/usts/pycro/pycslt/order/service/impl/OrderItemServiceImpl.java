package usts.pycro.pycslt.order.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.order.OrderItem;
import usts.pycro.pycslt.order.mapper.OrderItemMapper;
import usts.pycro.pycslt.order.service.OrderItemService;

/**
 * 订单项信息 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
