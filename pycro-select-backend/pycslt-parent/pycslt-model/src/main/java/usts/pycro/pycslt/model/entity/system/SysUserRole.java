package usts.pycro.pycslt.model.entity.system;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import usts.pycro.pycslt.model.entity.base.BaseEntity;

/**
 * 用户角色 实体类。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;

}
