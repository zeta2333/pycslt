package usts.pycro.pycslt.manager.service;

import usts.pycro.pycslt.model.vo.system.ValidateCodeVo;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 10:16
 */
public interface ValidateCodeService {
    /**
     * 生成图片验证码
     *
     * @return
     */
    ValidateCodeVo generateValidateCode();
}
