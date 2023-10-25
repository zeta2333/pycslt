package usts.pycro.pycslt.manager.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.mapper.SysMenuMapper;
import usts.pycro.pycslt.manager.service.SysMenuService;
import usts.pycro.pycslt.model.entity.system.SysMenu;

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
        // 父子关系查询
        return mapper.selectListWithRelationsByQuery(QueryWrapper.create()
                .where(SYS_MENU.PARENT_ID.eq(0))
                .orderBy(SYS_MENU.SORT_VALUE, true));
    }
}
