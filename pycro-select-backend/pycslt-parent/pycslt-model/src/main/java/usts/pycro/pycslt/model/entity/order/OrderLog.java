package usts.pycro.pycslt.model.entity.order;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Schema(description = "订单日志实体对象")
@Table("order_log")
public class OrderLog extends BaseLogicEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "操作人：用户；系统；后台管理员")
    private String operateUser;

    @Schema(description = "订单状态")
    private Integer processStatus;

    @Schema(description = "备注")
    private String note;

}