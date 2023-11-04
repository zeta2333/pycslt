package usts.pycro.pycslt.common.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import usts.pycro.pycslt.common.log.annotation.Log;
import usts.pycro.pycslt.common.log.enums.OperationStatus;
import usts.pycro.pycslt.common.log.service.SysOperLogService;
import usts.pycro.pycslt.common.log.util.LogUtil;
import usts.pycro.pycslt.model.entity.system.SysOperLog;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-04 20:31
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysOperLogService operLogService;

    @Around(value = "@annotation(sysLog)")
    public Object aroundAspect(ProceedingJoinPoint joinPoint, Log sysLog) {

        SysOperLog sysOperLog = new SysOperLog();

        // 业务方法调用之前，封装数据
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        // 业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            // 业务方法调用之后：正常返回
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog,
                    OperationStatus.NORMAL.getCode(), null);
        } catch (Throwable e) {
            // 业务方法调用之后：抛出异常
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog,
                    OperationStatus.ABNORMAL.getCode(), e.getMessage());
            // 此处需要抛出异常，使Spring底层事务切面类感知到，才能生效
            throw new RuntimeException(e);
        }
        // 保存日志信息到数据库
        operLogService.save(sysOperLog);
        return proceed;
    }
}
