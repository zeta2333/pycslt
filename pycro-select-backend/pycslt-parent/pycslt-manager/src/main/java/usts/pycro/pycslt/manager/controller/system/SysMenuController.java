package usts.pycro.pycslt.manager.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.service.system.SysMenuService;
import usts.pycro.pycslt.manager.service.system.SysRoleMenuService;
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

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    /**
     * 查询角色分配过的菜单id
     *
     * @param roleId
     * @return
     */
    @GetMapping("/findMenuIdsByRoleId/{roleId}")
    public Result<List<Long>> findMenuIdsByRoleId(@PathVariable("roleId") Long roleId) {
        List<Long> menuIds = sysRoleMenuService.findMenuIdsByRoleId(roleId);
        return Result.ok(menuIds);
    }


    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/removeById/{id}")
    public Result<?> removeById(@PathVariable("id") Long id) {
        sysMenuService.removeMenuById(id);
        return Result.ok(null);
    }


    /**
     * 修改
     *
     * @param sysMenu
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok(null);
    }

    /**
     * 添加
     *
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok(null);
    }

    /**
     * 查询所有菜单，递归层级返回
     *
     * @return
     */
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> menus = sysMenuService.findNodes();
        return Result.ok(menus);
    }
}
