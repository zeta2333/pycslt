package usts.pycro.pycslt.manager.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.service.SysUserService;
import usts.pycro.pycslt.model.dto.system.SysUserBo;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.common.Result;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-23 17:27
 */
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 条件分页查询
     *
     * @param sysUserBo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    public Result<Page<SysUser>> pageQuery(@RequestBody SysUserBo sysUserBo,
                                           @PathVariable("pageNum") Integer pageNum,
                                           @PathVariable("pageSize") Integer pageSize) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        page = sysUserService.pageQuery(page, sysUserBo);
        return Result.ok(page);
    }

    /**
     * 添加
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysUser sysUser) {
        sysUserService.saveUser(sysUser);
        return Result.ok(null);
    }

    /**
     * 修改
     *
     * @param sysUser
     * @return
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return Result.ok(null);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteById(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        return Result.ok(null);
    }
}
