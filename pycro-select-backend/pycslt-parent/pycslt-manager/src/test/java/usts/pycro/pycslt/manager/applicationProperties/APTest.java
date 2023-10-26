package usts.pycro.pycslt.manager.applicationProperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import usts.pycro.pycslt.manager.properties.ApplicationProperties;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-25 11:36
 */
@SpringBootTest
public class APTest {
    @Autowired
    private ApplicationProperties properties;

    @Test
    public void testGetProperties(){
        // List<String> noAuthUrls = properties.getAuth().getNoAuthUrls();
        // noAuthUrls.forEach(System.out::println);
        ApplicationProperties.Minio minio = properties.getMinio();
        System.out.println(minio.getEndpointUrl());
        System.out.println(minio.getAccessKey());
        System.out.println(minio.getSecretKey());
        System.out.println(minio.getBucketName());
    }
}
