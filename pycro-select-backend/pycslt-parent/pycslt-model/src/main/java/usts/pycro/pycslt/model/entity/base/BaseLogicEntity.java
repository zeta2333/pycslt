package usts.pycro.pycslt.model.entity.base;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseLogicEntity extends BaseEntity {

    @Schema(description = "是否删除")
    @Column(isLogicDelete = true)
    private Integer isDeleted;

}