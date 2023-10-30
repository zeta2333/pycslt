package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseEntity;

@Data
@Schema(description = "分类品牌实体类")
@Table("category_brand")
public class CategoryBrand extends BaseEntity {

    @Schema(description = "品牌id")
    private Long brandId;

    @Schema(description = "分类id")
    private Long categoryId;

    @Schema(description = "分类名称")
    @Column(ignore = true)
    private String categoryName;

    @Schema(description = "品牌名称")
    @Column(ignore = true)
    private String brandName;

    @Schema(description = "品牌logo")
    @Column(ignore = true)
    private String logo;

}