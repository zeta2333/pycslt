package usts.pycro.pycslt.model.dto.order;

import com.github.xiaoymin.knife4j.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "搜索条件实体类")
public class OrderStatisticsBo {

    @Schema(description = "开始时间")
    private String createTimeBegin;

    @Schema(description = "结束时间")
    private String createTimeEnd;

    public void setCreateTimeBegin(String createTimeBegin) {
        if (StrUtil.isNotBlank(createTimeBegin)) {
            this.createTimeBegin = createTimeBegin;
        }
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        if (StrUtil.isNotBlank(createTimeEnd)) {
            this.createTimeEnd = createTimeEnd;
        }
    }
}
