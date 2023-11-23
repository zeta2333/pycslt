package usts.pycro.pycslt.common.interceptor;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.utils.AuthContextUtil;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 17:55
 */
@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userInfoJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_APP.getValue() + token);
        // 将userInfo放到threadLocal中
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJson, UserInfo.class));
        return true;
    }
}
