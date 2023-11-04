package usts.pycro.pycslt.manager.system.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usts.pycro.pycslt.model.entity.system.SysOperLog;

/**
 * 操作日志记录 映射层。
 *
 * @author Pycro
 * @since 2023-11-04
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

}
