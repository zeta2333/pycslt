package usts.pycro.pycslt.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.manager.service.SysMenuService;
import usts.pycro.pycslt.model.entity.system.SysMenu;
import usts.pycro.pycslt.model.vo.common.Result;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 17:32
 */
@RestController
@RequestMapping(value = "/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询所有菜单，递归层级返回
     * @return
     */
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> menus = sysMenuService.findNodes();
        return Result.ok(menus);
    }
}
