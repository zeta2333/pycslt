package usts.pycro.pycslt.manager.sysRole;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.system.service.SysUserRoleService;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 16:32
 */
@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Test
    public void testGetAssignRoleIdsByUserId() {
        List<Long> assignedRoles = sysUserRoleService.getAssignedRoles(1L);
        assignedRoles.forEach(System.out::println);
    }
}
