package usts.pycro.pycslt.order.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.model.entity.order.OrderLog;
import usts.pycro.pycslt.order.mapper.OrderLogMapper;
import usts.pycro.pycslt.order.service.OrderLogService;

/**
 * 订单操作日志记录 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-27
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLog> implements OrderLogService {

}
