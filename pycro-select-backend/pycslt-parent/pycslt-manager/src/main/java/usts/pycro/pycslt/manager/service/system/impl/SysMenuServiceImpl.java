package usts.pycro.pycslt.manager.service.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.manager.mapper.SysMenuMapper;
import usts.pycro.pycslt.manager.service.system.SysMenuService;
import usts.pycro.pycslt.model.entity.system.SysMenu;

import java.util.Comparator;
import java.util.List;

import static usts.pycro.pycslt.model.entity.system.table.SysMenuTableDef.SYS_MENU;

/**
 * 菜单表 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 查询所有菜单，递归层级返回
     *
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        // 父子关系查询，查询顶层菜单（parentId=0），之后向下递归查询
        List<SysMenu> sysMenus = mapper.selectListWithRelationsByQuery(QueryWrapper.create()
                .where(SYS_MENU.PARENT_ID.eq(0))
                .orderBy(SYS_MENU.SORT_VALUE, true));
        // 排序
        sortMenu(sysMenus);

        return sysMenus;
    }

    private void sortMenu(List<SysMenu> sysMenus) {
        if (CollectionUtil.isNotEmpty(sysMenus)) {
            sysMenus.sort(Comparator.comparingInt(SysMenu::getSortValue));
            for (SysMenu sysMenu : sysMenus) {
                sortMenu(sysMenu.getChildren());
            }
        }
    }


    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void removeMenuById(Long id) {
        // 检查目标菜单是否存在子菜单
        long subMenuCount = count(QueryWrapper.create()
                .where(SYS_MENU.PARENT_ID.eq(id)));
        if (subMenuCount > 0) {
            throw new ServiceException("存在子菜单，无法删除");
        }
        // 不存在子菜单，则直接删除
        removeById(id);
    }
}
