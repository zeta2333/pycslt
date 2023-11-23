package usts.pycro.pycslt.model.bo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求参数实体类")
public class SysRoleBo {

    @Schema(description = "角色名称")
    private String roleName;

}
