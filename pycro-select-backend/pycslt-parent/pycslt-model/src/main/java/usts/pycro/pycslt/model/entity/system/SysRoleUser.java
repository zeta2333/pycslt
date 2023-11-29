package usts.pycro.pycslt.model.entity.system;

import lombok.Data;
import usts.pycro.pycslt.model.entity.base.BaseLogicEntity;

@Data
public class SysRoleUser extends BaseLogicEntity {

    private Long roleId;       // 角色id
    private Long userId;       // 用户id

}
