package usts.pycro.pycslt.model.entity.system;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import usts.pycro.pycslt.model.entity.base.BaseEntity;

/**
 * 角色菜单 实体类。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_role_menu")
public class SysRoleMenu extends BaseEntity {

    private Long roleId;

    private Long menuId;

    private Integer isHalf;

}
