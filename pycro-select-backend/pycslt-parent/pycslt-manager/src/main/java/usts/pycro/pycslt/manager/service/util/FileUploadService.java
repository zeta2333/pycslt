package usts.pycro.pycslt.manager.service.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 10:50
 */
public interface FileUploadService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
