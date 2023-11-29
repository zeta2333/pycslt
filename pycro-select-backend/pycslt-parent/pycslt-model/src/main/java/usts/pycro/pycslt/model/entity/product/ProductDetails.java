package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
@Table("product_details")
public class ProductDetails extends BaseLogicEntity {

    private Long productId;
    private String imageUrls;

}