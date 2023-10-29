package usts.pycro.pycslt.model.entity.product;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "分类实体类")
@Table("category")
public class Category extends BaseLogicEntity {

	@Schema(description = "分类名称")
	private String name;

	@Schema(description = "分类图片url")
	private String imageUrl;

	@Schema(description = "父节点id")
	private Long parentId;

	@Schema(description = "分类状态: 是否显示[0-不显示，1显示]")
	private Integer status;

	@Schema(description = "排序字段")
	private Integer orderNum;

	@Schema(description = "是否存在子节点")
	@Column(ignore = true)
	private Boolean hasChildren;

	@Schema(description = "子节点List集合")
	@Column(ignore = true)
	private List<Category> children;

}