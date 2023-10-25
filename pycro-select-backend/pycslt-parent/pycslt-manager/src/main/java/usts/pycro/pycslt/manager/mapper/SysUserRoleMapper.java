package usts.pycro.pycslt.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import usts.pycro.pycslt.model.entity.system.SysUserRole;

/**
 * 用户角色 映射层。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

}
