package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Schema(description = "品牌实体类")
@Table("brand")
public class Brand extends BaseLogicEntity {

    @Schema(description = "品牌名称")
    private String name;

    @Schema(description = "品牌logo")
    private String logo;

}