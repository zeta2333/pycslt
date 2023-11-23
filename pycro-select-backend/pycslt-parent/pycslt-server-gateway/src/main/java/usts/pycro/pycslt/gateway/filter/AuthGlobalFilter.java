package usts.pycro.pycslt.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.model.entity.user.UserInfo;
import usts.pycro.pycslt.model.vo.common.Result;
import usts.pycro.pycslt.model.vo.common.ResultCodeEnum;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 15:48
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("path: ${path}");
        // 获取用户信息
        UserInfo userInfo = getUserInfo(request);
        // 判断路径是否匹配 /api/**/auth/**,进行校验
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            if (Objects.isNull(userInfo)) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * 返回响应
     *
     * @param response
     * @param resultCodeEnum
     * @return
     */
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result<?> result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        // 指定编码为UTF-8，否则浏览器中会出现中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 根据request获取用户信息
     *
     * @param request
     * @return
     */
    private UserInfo getUserInfo(ServerHttpRequest request) {
        // 获取token
        String token = null;
        List<String> tokens = request.getHeaders().get("token");
        if (CollectionUtil.isNotEmpty(tokens)) {
            token = tokens.get(0);
        }
        if (StrUtil.isNotEmpty(token)) {
            String userInfoJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_APP.getValue() + token);
            if (StrUtil.isNotEmpty(userInfoJson)) {
                return JSON.parseObject(userInfoJson, UserInfo.class);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
