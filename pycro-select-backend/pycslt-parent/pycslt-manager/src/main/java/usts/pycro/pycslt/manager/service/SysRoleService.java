package usts.pycro.pycslt.manager.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import usts.pycro.pycslt.model.dto.system.SysRoleBo;
import usts.pycro.pycslt.model.entity.system.SysRole;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-20 16:01
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 分页查询
     * @param page 分页参数
     * @param sysRoleBo 查询对象
     * @return
     */
    Page<SysRole> pageQuery(Page<SysRole> page, SysRoleBo sysRoleBo);
}
