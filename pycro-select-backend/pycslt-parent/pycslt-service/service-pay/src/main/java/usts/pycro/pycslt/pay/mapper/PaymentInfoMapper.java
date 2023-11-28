package usts.pycro.pycslt.pay.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.pay.PaymentInfo;

/**
 * 付款信息表 映射层。
 *
 * @author Pycro
 * @since 2023-11-28
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

}
