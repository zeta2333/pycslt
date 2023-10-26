package usts.pycro.pycslt.manager.sysMenu;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.mapper.SysMenuMapper;
import usts.pycro.pycslt.manager.service.system.SysMenuService;
import usts.pycro.pycslt.manager.service.system.SysRoleMenuService;
import usts.pycro.pycslt.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

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
    public void testDelete(){
        sysMenuService.removeMenuById(1L);
    }

    @Test
    public void testSelectRoleMenuIds(){
        List<Long> menuIdsByRoleId = sysRoleMenuService.findMenuIdsByRoleId(9L);
        System.out.println(menuIdsByRoleId);
    }
}
