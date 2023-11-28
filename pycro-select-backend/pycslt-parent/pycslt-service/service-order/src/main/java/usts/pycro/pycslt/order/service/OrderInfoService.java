package usts.pycro.pycslt.order.service;

import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.bo.h5.OrderInfoBo;
import usts.pycro.pycslt.model.entity.order.OrderInfo;
import usts.pycro.pycslt.model.vo.h5.TradeVo;

/**
 * 订单 服务层。
 *
 * @author Pycro
 * @since 2023-11-27
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 获取下单信息
     *
     * @return
     */
    TradeVo getTrade();

    /**
     * 提交订单
     *
     * @param orderInfoBo
     * @return
     */
    Long submitOrder(OrderInfoBo orderInfoBo);

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfo(Long orderId);

    /**
     * 立即购买
     *
     * @param skuId
     * @return
     */
    TradeVo buy(Long skuId);

    /**
     * 获取订单分页列表
     *
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    /**
     * 根据订单编号获取订单信息
     *
     * @param orderNo
     * @return
     */
    OrderInfo getByOrderNo(String orderNo);

    /**
     * 更新订单状态
     *
     * @param orderNo
     * @param orderStatus
     */
    void updateOrderStatus(String orderNo, Integer orderStatus);
}
