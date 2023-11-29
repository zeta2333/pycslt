package usts.pycro.pycslt.manager.util.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.enums.RedisKeyEnum;
import usts.pycro.pycslt.manager.util.service.ValidateCodeService;
import usts.pycro.pycslt.model.vo.system.ValidateCodeVo;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 10:17
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成图片验证码
     *
     * @return
     */
    @Override
    public ValidateCodeVo generateValidateCode() {
        // UUID随机生成key
        String key = UUID.randomUUID().toString(true);

        // 验证码为4位随机字符
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode();// 生成的4位验证码的值
        String imageBase64 = circleCaptcha.getImageBase64();// 验证码图片（base64编码格式）
        redisTemplate.opsForValue().set(RedisKeyEnum.USER_VALIDATE.getValue() + key,
                codeValue,
                5,
                TimeUnit.MINUTES);

        // 返回validateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);// redis中存储数据的key
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);// 验证码图片

        return validateCodeVo;
    }
}
