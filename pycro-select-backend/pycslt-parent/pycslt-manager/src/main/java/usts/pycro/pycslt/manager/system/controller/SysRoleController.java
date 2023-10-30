package usts.pycro.pycslt.manager.system.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.system.service.SysRoleService;
import usts.pycro.pycslt.manager.system.service.SysUserRoleService;
import usts.pycro.pycslt.model.dto.system.AssignMenuBo;
import usts.pycro.pycslt.model.dto.system.SysRoleBo;
import usts.pycro.pycslt.model.entity.system.SysRole;
import usts.pycro.pycslt.model.vo.common.Result;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-20 16:00
 */
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 为角色分配菜单
     * @return
     */
    @PostMapping("/doAssign")
    public Result<?> doAssign(@RequestBody AssignMenuBo assignMenuBo) {
        sysRoleService.doAssign(assignMenuBo);
        return Result.ok(null);
    }

    /**
     * 查询用户已分配的角色id
     *
     * @param userId
     * @return
     */
    @GetMapping("/getAssignedRoles/{userId}")
    public Result<List<Long>> getAssignedRoles(@PathVariable("userId") Long userId) {
        List<Long> list = sysUserRoleService.getAssignedRoles(userId);
        return Result.ok(list);
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @GetMapping("/getAllRoles")
    public Result<List<SysRole>> getAllRoles() {
        List<SysRole> roles = sysRoleService.list();
        return Result.ok(roles);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteById(@PathVariable("id") Long id) {
        sysRoleService.removeById(id);
        return Result.ok(null);
    }

    /**
     * 修改
     *
     * @param sysRole 角色信息
     * @return ?
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysRole sysRole) {
        sysRoleService.updateById(sysRole);
        return Result.ok(null);
    }

    /**
     * 添加
     *
     * @param sysRole 角色信息
     * @return ?
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.ok(null);
    }

    /**
     * 分页查询
     *
     * @param sysRoleBo 查询参数
     * @param pageNum   页码
     * @param pageSize  页面大小
     * @return role
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<Page<SysRole>> pageQuery(@RequestBody SysRoleBo sysRoleBo,
                                           @PathVariable("pageNum") Integer pageNum,
                                           @PathVariable("pageSize") Integer pageSize) {
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        page = sysRoleService.pageQuery(page, sysRoleBo);
        return Result.ok(page);
    }
}
