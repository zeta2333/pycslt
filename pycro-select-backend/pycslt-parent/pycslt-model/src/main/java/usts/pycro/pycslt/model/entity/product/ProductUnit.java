package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Schema(description = "产品单元实体类")
@Table("product_unit")
public class ProductUnit extends BaseLogicEntity {

	@Schema(description = "名称")
	private String name;

}