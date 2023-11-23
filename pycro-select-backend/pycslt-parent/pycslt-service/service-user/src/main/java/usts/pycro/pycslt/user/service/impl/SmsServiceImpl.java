package usts.pycro.pycslt.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.user.service.SmsService;
import usts.pycro.pycslt.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 11:16
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送验证码
     *
     * @param phone
     */
    @Override
    public void sendCode(String phone) {
        String code = redisTemplate.opsForValue().get("VerificationCode:${phone}:");
        if (StrUtil.isNotBlank(code)) {
            return;
        }
        // 生成验证码
        code = RandomUtil.randomString("0123456789", 6);
        // 将生成的验证码放入redis中，并设置过期时间
        redisTemplate.opsForValue().set("VerificationCode:${phone}:", code, 3, TimeUnit.MINUTES);
        // 向手机号发送验证码
        sendMessage(phone, code);
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param code
     */
    private void sendMessage(String phone, String code) {
        // host path method 固定，不可修改
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "c94b51d2c29949eba89a9e1a2d66dfc1";
        Map<String, String> headers = new HashMap<>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        // 根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> queries = new HashMap<>();
        Map<String, String> bodies = new HashMap<>();
        bodies.put("content", "code:${code}");
        bodies.put("template_id", "CST_ptdie100");
        bodies.put("phone_number", "${phone}");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, queries, bodies);
            System.out.println(response.toString());
            // 获取response的body
            // System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
