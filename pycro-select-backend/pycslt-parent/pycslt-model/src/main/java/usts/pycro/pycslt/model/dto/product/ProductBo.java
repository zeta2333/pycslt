package usts.pycro.pycslt.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Schema(description = "商品搜索条件实体类")
public class ProductBo extends BaseLogicEntity {

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "一级分类id")
    private Long category1Id;

    @Schema(description = "二级分类id")
    private Long category2Id;

    @Schema(description = "三级分类id")
    private Long category3Id;

}
