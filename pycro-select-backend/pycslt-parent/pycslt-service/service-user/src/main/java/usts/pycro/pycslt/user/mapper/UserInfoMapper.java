package usts.pycro.pycslt.user.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.user.UserInfo;

/**
 * 会员表 映射层。
 *
 * @author Pycro
 * @since 2023-11-23
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
