package usts.pycro.pycslt.model.entity.system;

import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

import java.util.List;

@Schema(description = "系统菜单实体类")
@Data
@Table("sys_menu")
public class SysMenu extends BaseLogicEntity {

    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "节点标题")
    private String title;

    @Schema(description = "组件名称")
    private String component;

    @Schema(description = "排序值")
    private Integer sortValue;

    @Schema(description = "状态(0:禁止,1:正常)")
    private Integer status;

    // 下级列表
    @Schema(description = "子节点")
    @RelationOneToMany(selfField = "id", targetField = "parentId")
    private List<SysMenu> children;

}