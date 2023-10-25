package usts.pycro.pycslt.model.entity.order;

import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics extends BaseLogicEntity {

    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;
    
}