package usts.pycro.pycslt.manager.system.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.system.SysMenu;

/**
 * 菜单表 映射层。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
