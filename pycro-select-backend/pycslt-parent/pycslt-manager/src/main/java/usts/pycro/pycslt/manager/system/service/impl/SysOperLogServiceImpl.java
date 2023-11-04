package usts.pycro.pycslt.manager.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usts.pycro.pycslt.common.log.service.SysOperLogService;
import usts.pycro.pycslt.manager.system.mapper.SysOperLogMapper;
import usts.pycro.pycslt.model.entity.system.SysOperLog;

/**
 * 操作日志记录 服务层实现。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {
}
