package usts.pycro.pycslt.manager.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.service.SysRoleService;
import usts.pycro.pycslt.model.dto.system.SysRoleBo;
import usts.pycro.pycslt.model.entity.system.SysRole;
import usts.pycro.pycslt.model.vo.common.Result;

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