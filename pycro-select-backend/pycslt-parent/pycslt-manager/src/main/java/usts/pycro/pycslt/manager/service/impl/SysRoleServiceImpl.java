package usts.pycro.pycslt.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.manager.mapper.SysRoleMapper;
import usts.pycro.pycslt.manager.service.SysRoleService;
import usts.pycro.pycslt.model.dto.system.SysRoleBo;
import usts.pycro.pycslt.model.entity.system.SysRole;

import static usts.pycro.pycslt.model.entity.system.table.SysRoleTableDef.SYS_ROLE;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-20 16:02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 分页查询
     *
     * @param page
     * @param sysRoleBo
     * @return
     */
    @Override
    public Page<SysRole> pageQuery(Page<SysRole> page, SysRoleBo sysRoleBo) {
        String roleName = sysRoleBo.getRoleName();
        return mapper.paginate(page, QueryWrapper.create()
                .select(SYS_ROLE.DEFAULT_COLUMNS)
                .where(SYS_ROLE.ROLE_NAME.like(roleName, StrUtil.isNotBlank(roleName)))
                .orderBy(SYS_ROLE.ID, false));
    }
}
