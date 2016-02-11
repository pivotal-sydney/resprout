package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

@SpringBootApplication
@EnableScheduling
public class ResproutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResproutApplication.class, args);
    }

    @Bean
    public FileSystem providesFileSystem() {
        return FileSystems.getDefault();
    }
}
