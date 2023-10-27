package usts.pycro.pycslt.manager.util;

import usts.pycro.pycslt.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-27 18:04
 */
public class MenuHelper {
    public static List<SysMenu> buildMenuTree(List<SysMenu> sysMenus) {
        List<SysMenu> menuTree = new ArrayList<>();
        // 找到顶级菜单，然后构建子菜单
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId().equals(0L)) {
                menuTree.add(findChildren(sysMenu, sysMenus));
            }
        }
        return menuTree;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenus) {
        sysMenu.setChildren(new ArrayList<>());
        for (SysMenu subMenu : sysMenus) {
            if (subMenu.getParentId().equals(sysMenu.getId())) {
                sysMenu.getChildren().add(findChildren(subMenu, sysMenus));
            }
        }
        return sysMenu;
    }
}
