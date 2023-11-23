package usts.pycro.pycslt.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-23 11:10
 */
@SpringBootApplication
@ComponentScan({"usts.pycro.pycslt"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
