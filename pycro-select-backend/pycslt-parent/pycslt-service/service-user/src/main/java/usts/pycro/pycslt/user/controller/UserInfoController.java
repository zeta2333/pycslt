package usts.pycro.pycslt.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usts.pycro.pycslt.model.bo.h5.UserLoginBo;
import usts.pycro.pycslt.model.bo.h5.UserRegisterBo;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.h5.UserInfoVo;
import usts.pycro.pycslt.user.service.UserInfoService;

/**
 * 会员表 控制层。
 *
 * @author Pycro
 * @since 2023-11-23
 */
@Tag(name = "会员用户接口")
@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取当前用户登录信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.ok(userInfoVo);
    }


    /**
     * 用户登录
     *
     * @param userLoginBo
     * @return
     */
    @Operation(summary = "会员登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody UserLoginBo userLoginBo) {
        String token = userInfoService.login(userLoginBo);
        return Result.ok(token);
    }


    /**
     * 用户注册
     *
     * @param userRegisterBo
     * @return
     */
    @Operation(summary = "会员注册")
    @PostMapping("register")
    public Result<?> register(@RequestBody UserRegisterBo userRegisterBo) {
        userInfoService.register(userRegisterBo);
        return Result.ok(null);
    }
}
