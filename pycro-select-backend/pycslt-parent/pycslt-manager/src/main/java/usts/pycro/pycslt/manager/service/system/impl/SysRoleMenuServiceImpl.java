package usts.pycro.pycslt.manager.service.system.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import usts.pycro.pycslt.model.entity.system.SysRoleMenu;
import usts.pycro.pycslt.manager.mapper.SysRoleMenuMapper;
import usts.pycro.pycslt.manager.service.system.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

import static usts.pycro.pycslt.model.entity.system.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 角色菜单 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    /**
     * 查询角色已分配的所有菜单id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> findMenuIdsByRoleId(Long roleId) {
        return list(QueryWrapper.create()
                .select(SYS_ROLE_MENU.MENU_ID)
                .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId)))
                .stream()
                .map(SysRoleMenu::getMenuId).toList();
    }
}
