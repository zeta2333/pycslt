package usts.pycro.pycslt.model.entity.product;

import usts.pycro.pycslt.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}