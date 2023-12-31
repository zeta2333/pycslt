package usts.pycro.pycslt.manager.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.system.service.SysMenuService;
import usts.pycro.pycslt.manager.system.service.SysUserService;
import usts.pycro.pycslt.manager.util.service.ValidateCodeService;
import usts.pycro.pycslt.model.bo.system.LoginBo;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.system.LoginVo;
import usts.pycro.pycslt.model.vo.system.SysMenuVo;
import usts.pycro.pycslt.model.vo.system.ValidateCodeVo;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 15:46
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/menus")
    public Result<List<SysMenuVo>> findMenusByUserId() {
        List<SysMenuVo> sysMenuVos = sysMenuService.findMenusByUserId();
        return Result.ok(sysMenuVos);
    }

    /**
     * 用户登出
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public Result<?> logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.ok(null);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo() {
        // 从threadLocal查询用户信息
        SysUser sysUser = AuthContextUtil.getSysUser();
        // 返回用户信息
        return Result.ok(sysUser);
    }

    /**
     * 生成图片验证码
     *
     * @return
     */
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.ok(validateCodeVo);
    }

    /**
     * 登录
     *
     * @param loginBo
     * @return
     */
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody @Valid LoginBo loginBo) {
        LoginVo loginVo = sysUserService.login(loginBo);
        return Result.ok(loginVo);
    }

}