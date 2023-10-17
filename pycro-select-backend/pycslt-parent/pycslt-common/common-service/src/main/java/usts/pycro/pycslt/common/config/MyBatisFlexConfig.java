package usts.pycro.pycslt.common.config;

import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 16:43
 */
@Configuration
public class MyBatisFlexConfig {
    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");


    public MyBatisFlexConfig() {
        // 开启审计功能
        AuditManager.setAuditEnable(true);

        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                logger.info("flex exec sql took {} ms >>> \n {}",
                        auditMessage.getElapsedTime(),
                        auditMessage.getFullSql()
                )
        );
    }
}
