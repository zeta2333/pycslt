package usts.pycro.pycslt.manager.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.mapper.SysUserRoleMapper;
import usts.pycro.pycslt.manager.service.SysUserRoleService;
import usts.pycro.pycslt.model.entity.system.SysUserRole;

import java.util.List;

import static usts.pycro.pycslt.model.entity.system.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * 用户角色 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 根据userId获取已分配的roleId
     *
     * @param userId
     * @return
     */
    @Override
    public List<Long> getAssignedRoles(Long userId) {
        return list(QueryWrapper.create()
                .select(SYS_USER_ROLE.ROLE_ID)
                .where(SYS_USER_ROLE.USER_ID.eq(userId)))
                .stream()
                .map(SysUserRole::getRoleId).toList();
    }
}
