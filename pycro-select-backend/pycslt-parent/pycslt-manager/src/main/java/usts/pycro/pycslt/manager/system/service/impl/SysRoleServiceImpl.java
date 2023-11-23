package usts.pycro.pycslt.manager.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.system.mapper.SysRoleMapper;
import usts.pycro.pycslt.manager.system.service.SysRoleMenuService;
import usts.pycro.pycslt.manager.system.service.SysRoleService;
import usts.pycro.pycslt.model.bo.system.AssignMenuBo;
import usts.pycro.pycslt.model.bo.system.SysRoleBo;
import usts.pycro.pycslt.model.entity.system.SysRole;
import usts.pycro.pycslt.model.entity.system.SysRoleMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static usts.pycro.pycslt.model.entity.system.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static usts.pycro.pycslt.model.entity.system.table.SysRoleTableDef.SYS_ROLE;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-20 16:02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 角色分配菜单
     *
     * @param assignMenuBo
     */
    @Override
    public void doAssign(AssignMenuBo assignMenuBo) {
        Long roleId = assignMenuBo.getRoleId();
        List<Map<String, Number>> menuIdList = assignMenuBo.getMenuIdList();
        // 删除之前角色分配的菜单
        sysRoleMenuService.remove(QueryWrapper.create()
                .where(SYS_ROLE_MENU.ROLE_ID.eq(roleId)));
        // 重新分配菜单
        if (CollectionUtil.isEmpty(menuIdList)) {
            return;
        }
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (Map<String, Number> map : menuIdList) {
            Long menuId = map.get("id").longValue();
            Integer isHalf = map.get("isHalf").intValue();
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setIsHalf(isHalf);
            roleMenus.add(roleMenu);
        }
        sysRoleMenuService.saveBatch(roleMenus);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param sysRoleBo
     * @return
     */
    @Override
    public Page<SysRole> pageQuery(Page<SysRole> page, SysRoleBo sysRoleBo) {
        String roleName = sysRoleBo.getRoleName();
        return mapper.paginate(page, QueryWrapper.create()
                .select(SYS_ROLE.DEFAULT_COLUMNS)
                .where(SYS_ROLE.ROLE_NAME.like(roleName, StrUtil.isNotBlank(roleName)))
                .orderBy(SYS_ROLE.ID, false));
    }
}
