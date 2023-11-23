package usts.pycro.pycslt.model.bo.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class UserLoginBo {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}