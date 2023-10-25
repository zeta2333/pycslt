package usts.pycro.pycslt.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import usts.pycro.pycslt.manager.service.FileUploadService;
import usts.pycro.pycslt.model.vo.common.Result;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 10:49
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;


    /**
     * @param file
     * @return
     */
    @PostMapping("/fileUpload")
    // <input type="file" name="file" />
    public Result<?> fileUpload(@RequestParam("file") MultipartFile file) {
        // 获取上传的文件
        // 调用service的方法上传，返回minio的路径
        String url = fileUploadService.upload(file);
        return Result.ok(url);
    }
}
