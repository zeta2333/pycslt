package usts.pycro.pycslt.manager.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import usts.pycro.pycslt.model.entity.system.SysRoleMenu;
import usts.pycro.pycslt.manager.system.mapper.SysRoleMenuMapper;
import usts.pycro.pycslt.manager.system.service.SysRoleMenuService;
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
     * 注意：此处查询的菜单要求isHalf属性为0
     * 因为 父级菜单被判定为选中 的条件是 其所有子菜单都被选中，此时isHalf为0;
     * 如果父级菜单的isHalf为1，表示其只有部分子菜单被选中，父级菜单此时判定为不被选中状态
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> findMenuIdsByRoleId(Long roleId) {
        return list(QueryWrapper.create()
                .select(SYS_ROLE_MENU.MENU_ID)
                .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId))
                .and(SYS_ROLE_MENU.IS_HALF.eq(0)))
                .stream()
                .map(SysRoleMenu::getMenuId).toList();
    }
}
