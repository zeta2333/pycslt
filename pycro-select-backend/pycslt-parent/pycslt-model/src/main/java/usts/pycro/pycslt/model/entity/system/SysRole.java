package usts.pycro.pycslt.model.entity.system;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import usts.pycro.pycslt.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色实体类")
@Table("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    @Column("role_name")
    private String roleName;

    @Schema(description = "角色编码")
    @Column("role_code")
    private String roleCode;

    @Schema(description = "描述")
    private String description;

}
