package usts.pycro.pycslt.model.entity.order;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("order_statistics")
public class OrderStatistics extends BaseLogicEntity {

    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;

}