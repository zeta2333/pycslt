package usts.pycro.pycslt.manager.service;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.system.SysMenu;

import java.util.List;

/**
 * 菜单表 服务层。
 *
 * @author Pycro
 * @since 2023-10-25
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询所有菜单，递归层级返回
     * @return
     */
    List<SysMenu> findNodes();
}
