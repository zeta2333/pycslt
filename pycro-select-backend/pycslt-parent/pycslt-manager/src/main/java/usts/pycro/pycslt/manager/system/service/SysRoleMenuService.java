package usts.pycro.pycslt.manager.system.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.system.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单 服务层。
 *
 * @author Pycro
 * @since 2023-10-25
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 查询角色已分配的所有菜单id
     *
     * @param roleId
     * @return
     */
    List<Long> findMenuIdsByRoleId(Long roleId);
}
