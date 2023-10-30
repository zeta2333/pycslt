package usts.pycro.pycslt.manager.system.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.model.entity.system.SysUser;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 15:47
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1 获取请求方式
        String method = request.getMethod();
        // 如果请求方式为options（预检请求），则直接放行
        if (StrUtil.equalsIgnoreCase(HttpMethod.OPTIONS.name(), method)) {
            return true;
        }
        // 2 从请求头获取token
        String token = request.getHeader("token");
        // 3 如果token为空，返回错误提示
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }
        // 4 如果token不为空，拿着token查询redis
        String userInfo = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN.getValue() + token);
        // 5 如果redis查询不到数据，返回错误提示
        if (StrUtil.isEmpty(userInfo)) {
            responseNoLoginInfo(response);
            return false;
        }
        // 6 如果redis查询到数据，把查询到的用户信息放到ThreadLocal里面
        SysUser sysUser = JSON.parseObject(userInfo, SysUser.class);
        AuthContextUtil.set(sysUser);
        // 7 把redis用户信息更新过期时间
        redisTemplate.expire(RedisKeyEnum.USER_LOGIN.getValue() + token,
                1440, TimeUnit.MINUTES);
        // 8 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 删除ThreadLocal
        AuthContextUtil.remove();
    }

    // 响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
