package usts.pycro.pycslt.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usts.pycro.pycslt.manager.service.SysUserService;
import usts.pycro.pycslt.model.dto.system.LoginDto;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.system.LoginVo;

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

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody @Valid LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.ok(loginVo);
    }

}