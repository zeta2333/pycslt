package usts.pycro.pycslt.manager.service.util.impl;

import cn.hutool.core.date.DateUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usts.pycro.pycslt.common.exception.ServiceException;
import usts.pycro.pycslt.manager.properties.ApplicationProperties;
import usts.pycro.pycslt.manager.service.util.FileUploadService;

import java.util.Date;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 10:50
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private ApplicationProperties properties;


    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        String endpointUrl = properties.getMinio().getEndpointUrl();
        String accessKey = properties.getMinio().getAccessKey();
        String secretKey = properties.getMinio().getSecretKey();
        String bucketName = properties.getMinio().getBucketName();
        try {
            // 创建minio客户端
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpointUrl)
                            .credentials(accessKey, secretKey)
                            .build();

            // 如果bucket不存在则创建
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 创建bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.info("Bucket '{}' already exists.", bucketName);
            }


            // 文件上传
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String datetimePrefix = DateUtil.format(new Date(), "yyyyMMddHHmmss");
            String filename = dateDir + "/" + datetimePrefix + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());
            log.info("'{}' is successfully uploaded to bucket '{}'.", filename, bucketName);

            // 获取并返回上传文件路径
            String fileUrl = endpointUrl + "/" + bucketName + "/" + filename;
            log.info("文件上传路径为:{}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件上传失败");
        }
    }
}
