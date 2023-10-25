package usts.pycro.pycslt.model.entity.product;

import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseLogicEntity {

	private Long productId;
	private String imageUrls;

}