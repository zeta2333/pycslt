package usts.pycro.pycslt.manager.service.system;

import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.entity.system.SysMenu;
import usts.pycro.pycslt.model.vo.system.SysMenuVo;

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

    /**
     * 删除
     * @param id
     */
    void removeMenuById(Long id);

    /**
     * 查询用户拥有的菜单
     * @return
     */
    List<SysMenuVo> findMenusByUserId();

    /**
     * 添加菜单
     * @param sysMenu
     */
    void saveMenu(SysMenu sysMenu);
}
