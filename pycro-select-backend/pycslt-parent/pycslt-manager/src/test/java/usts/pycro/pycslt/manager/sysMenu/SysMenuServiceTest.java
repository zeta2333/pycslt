package usts.pycro.pycslt.manager.sysMenu;

import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.system.mapper.SysMenuMapper;
import usts.pycro.pycslt.manager.system.service.SysMenuService;
import usts.pycro.pycslt.manager.system.service.SysRoleMenuService;
import usts.pycro.pycslt.model.entity.system.SysMenu;
import usts.pycro.pycslt.model.vo.system.SysMenuVo;

import java.util.ArrayList;
import java.util.List;

import static usts.pycro.pycslt.model.entity.system.table.SysMenuTableDef.SYS_MENU;
import static usts.pycro.pycslt.model.entity.system.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static usts.pycro.pycslt.model.entity.system.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 17:22
 */
@SpringBootTest
public class SysMenuServiceTest {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Test
    public void testRecursiveSelect() {
        // List<SysMenu> sysMenus = sysMenuMapper.selectListWithRelationsByQuery(QueryWrapper.create()
        //         .where(SYS_MENU.PARENT_ID.eq(0))
        //         .orderBy(SYS_MENU.SORT_VALUE, true));
        // System.out.println(JSON.toJSONString(sysMenus));
        Long i = 1L;
    }

    @Test
    public void testBuildMenuTree() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAll();
        List<SysMenu> menuTree = new ArrayList<>();
        // 找到顶级菜单，然后构建子菜单
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId().equals(0L)) {
                menuTree.add(findChildren(sysMenu, sysMenus));
            }
        }
        System.out.println(JSON.toJSONString(menuTree));
    }


    private SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenus) {
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu subMenu : sysMenus) {
            if (subMenu.getParentId().equals(sysMenu.getId())) {
                sysMenu.getChildren().add(findChildren(subMenu, sysMenus));
            }
        }
        return sysMenu;
    }

    @Test
    public void testDelete() {
        sysMenuService.removeMenuById(1L);
    }

    @Test
    public void testSelectRoleMenuIds() {
        List<Long> menuIdsByRoleId = sysRoleMenuService.findMenuIdsByRoleId(9L);
        System.out.println(menuIdsByRoleId);
    }

    @Test
    public void testGetMenusByUserId() {
        List<SysMenu> sysMenus = sysMenuMapper.selectListWithRelationsByQuery(
                QueryWrapper.create()
                        .from(SYS_MENU)
                        .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID))
                        .leftJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
                        .where(SYS_USER_ROLE.USER_ID.eq(1))
                        .and(SYS_MENU.PARENT_ID.eq(0)));
        System.out.println(sysMenus);
    }

    @Test
    public void testServiceMethod() {
        List<SysMenuVo> menuVos = sysMenuService.findMenusByUserId();
        System.out.println(JSON.toJSONString(menuVos));
    }

}
