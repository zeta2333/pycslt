package usts.pycro.pycslt.manager.system.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.system.SysUserRole;

import java.util.List;

/**
 * 用户角色 服务层。
 *
 * @author Pycro
 * @since 2023-10-25
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据userId获取已分配的roleId
     *
     * @param userId
     * @return
     */
    List<Long> getAssignedRoles(Long userId);
}
