package usts.pycro.pycslt.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.manager.service.SysUserService;
import usts.pycro.pycslt.manager.service.ValidateCodeService;
import usts.pycro.pycslt.model.dto.system.LoginDto;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.system.LoginVo;
import usts.pycro.pycslt.model.vo.system.ValidateCodeVo;
import usts.pycro.pycslt.utils.AuthContextUtil;

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
        SysUser sysUser = AuthContextUtil.get();
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
     * @param loginDto
     * @return
     */
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody @Valid LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.ok(loginVo);
    }

}