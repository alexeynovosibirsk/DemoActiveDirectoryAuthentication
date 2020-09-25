package demo.alexeynovosibirsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import demo.alexeynovosibirsk.common.DirectoryCreater;
import demo.alexeynovosibirsk.security.SecurityConfigFileHandler;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableAsync
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        DirectoryCreater.makeDirs();

        SecurityConfigFileHandler.createFile();
    }
}
