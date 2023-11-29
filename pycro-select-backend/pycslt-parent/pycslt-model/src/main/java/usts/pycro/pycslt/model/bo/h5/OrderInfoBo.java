package usts.pycro.pycslt.model.bo.h5;

import lombok.Data;
import usts.pycro.pycslt.model.entity.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Pycro
 */
@Data
public class OrderInfoBo {

    // 送货地址id
    private Long userAddressId;

    // 运费
    private BigDecimal freightFee;

    // 备注
    private String remark;

    // 订单明细
    private List<OrderItem> orderItemList;
}