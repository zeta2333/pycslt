package usts.pycro.pycslt.model.entity.product;

import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "品牌实体类")
public class Brand extends BaseLogicEntity {

	@Schema(description = "品牌名称")
	private String name;

	@Schema(description = "品牌logo")
	private String logo;

}