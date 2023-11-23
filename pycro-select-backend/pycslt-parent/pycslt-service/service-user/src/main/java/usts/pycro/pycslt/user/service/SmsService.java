package usts.pycro.pycslt.user.service;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 11:16
 */
public interface SmsService {
    /**
     * 发送验证码
     *
     * @param phone
     */
    void sendCode(String phone);
}
