package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Schema(description = "商品规格实体类")
@Table("product_spec")
public class ProductSpec extends BaseLogicEntity {

    @Schema(description = "规格名称")
    private String specName;   // 规格名称

    @Schema(description = "规格值")
    private String specValue;  // 规格值

}