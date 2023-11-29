package usts.pycro.pycslt.manager.sysUser;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.system.service.SysUserRoleService;
import usts.pycro.pycslt.manager.system.service.SysUserService;
import usts.pycro.pycslt.model.bo.system.SysUserBo;
import usts.pycro.pycslt.model.entity.system.SysUser;

import static usts.pycro.pycslt.model.entity.system.table.SysUserRoleTableDef.SYS_USER_ROLE;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-24 10:08
 */
@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Test
    public void testPageQuery() {
        Page<SysUser> page = new Page<>(1, 20);
        SysUserBo sysUserBo = new SysUserBo();
        sysUserBo.setKeyword("   a   ");
        Page<SysUser> sysUserPage = sysUserService.pageQuery(page, sysUserBo);
        System.out.println(sysUserPage);
    }

    @Test
    public void testDelete() {
        sysUserRoleService.remove(QueryWrapper.create()
                .where(SYS_USER_ROLE.USER_ID.eq(1)));
    }
}
