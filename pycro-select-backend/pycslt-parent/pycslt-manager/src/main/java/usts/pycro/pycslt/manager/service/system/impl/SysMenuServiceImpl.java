package usts.pycro.pycslt.manager.service.system.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.manager.mapper.SysMenuMapper;
import usts.pycro.pycslt.manager.mapper.SysRoleMenuMapper;
import usts.pycro.pycslt.manager.service.system.SysMenuService;
import usts.pycro.pycslt.manager.util.MenuHelper;
import usts.pycro.pycslt.model.entity.system.SysMenu;
import usts.pycro.pycslt.model.entity.system.SysRoleMenu;
import usts.pycro.pycslt.model.vo.system.SysMenuVo;
import usts.pycro.pycslt.utils.AuthContextUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.distinct;
import static usts.pycro.pycslt.model.entity.system.table.SysMenuTableDef.SYS_MENU;
import static usts.pycro.pycslt.model.entity.system.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static usts.pycro.pycslt.model.entity.system.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * 菜单表 服务层实现。
 *
 * @author Pycro
 * @since 2023-10-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

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
     * 添加菜单
     *
     * @param sysMenu
     */
    @Override
    public void saveMenu(SysMenu sysMenu) {
        // 保存
        save(sysMenu);
        // 将父菜单的isHalf修改为1
        updateSysRoleMenu(sysMenu);
    }

    /**
     * 修改sysRoleMenu中的父菜单的isHalf为1
     *
     * @param sysMenu
     */
    private void updateSysRoleMenu(SysMenu sysMenu) {
        SysMenu parentMenu = mapper.selectOneById(sysMenu.getParentId());
        if (parentMenu == null) {
            return;
        }
        Long parentId = parentMenu.getId();
        // 修改roleMenu记录
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectListByQuery(QueryWrapper.create()
                .where(SYS_ROLE_MENU.MENU_ID.eq(parentId)));
        for (SysRoleMenu roleMenu : roleMenus) {
            roleMenu.setIsHalf(1);
            sysRoleMenuMapper.update(roleMenu);
        }
        // 递归调用
        updateSysRoleMenu(parentMenu);
    }

    /**
     * 查询用户拥有的菜单
     *
     * @return
     */
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        // 获取当前用户id
        Long userId = AuthContextUtil.get().getId();
        // 根据用户id查询菜单(注意去重)
        List<SysMenu> sysMenus = mapper.selectListByQuery(QueryWrapper.create()
                .select(distinct(SYS_MENU.DEFAULT_COLUMNS))
                .from(SYS_MENU)
                .innerJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID))
                .innerJoin(SYS_USER_ROLE).on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
                .where(SYS_USER_ROLE.USER_ID.eq(userId))
        );
        // 构建菜单树形结构
        List<SysMenu> menuTree = MenuHelper.buildMenuTree(sysMenus);
        // 构建数据结构返回
        return buildMenuVos(menuTree);
    }

    /**
     * 构建子菜单Vo
     *
     * @param sysMenus
     * @return
     */
    private List<SysMenuVo> buildMenuVos(List<SysMenu> sysMenus) {
        List<SysMenuVo> sysMenuVos = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            if (CollectionUtil.isNotEmpty(sysMenu.getChildren())) { // 防止空指针
                sysMenuVo.setChildren(buildMenuVos(sysMenu.getChildren()));
            }
            sysMenuVos.add(sysMenuVo);
        }
        return sysMenuVos;
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
