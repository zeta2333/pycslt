package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

import java.util.List;

@Data
@Schema(description = "商品实体类")
@Table("product")
public class Product extends BaseLogicEntity {

    @Schema(description = "商品名称")
    private String name;                    // 商品名称

    @Schema(description = "品牌id")

    private Long brandId;                    // 品牌ID

    @Schema(description = "一级分类id")
    private Long category1Id;                // 一级分类id

    @Schema(description = "二级分类id")
    private Long category2Id;                // 二级分类id

    @Schema(description = "三级分类id")
    private Long category3Id;                // 三级分类id

    @Schema(description = "计量单位")
    private String unitName;                // 计量单位

    @Schema(description = "轮播图url")
    private String sliderUrls;                // 轮播图

    @Schema(description = "商品规格值json串")
    private String specValue;                // 商品规格值json串

    @Schema(description = "线上状态：0-初始值，1-上架，-1-自主下架")
    private Integer status;                    // 线上状态：0-初始值，1-上架，-1-自主下架

    @Schema(description = "审核状态")
    private Integer auditStatus;            // 审核状态

    @Schema(description = "审核信息")
    private String auditMessage;            // 审核信息

    // 扩展的属性，用来封装响应的数据
    @Schema(description = "品牌名称")
    @Column(ignore = true)
    private String brandName;                // 品牌

    @Schema(description = "一级分类名称")
    @Column(ignore = true)
    private String category1Name;            // 一级分类

    @Schema(description = "二级分类名称")
    @Column(ignore = true)
    private String category2Name;            // 二级分类

    @Schema(description = "三级分类名称")
    @Column(ignore = true)
    private String category3Name;            // 三级分类

    @Schema(description = "sku列表集合")
    @Column(ignore = true)
    private List<ProductSku> productSkuList;        // sku列表集合

    @Schema(description = "图片详情列表")
    @Column(ignore = true)
    private String detailsImageUrls;                // 图片详情列表

}