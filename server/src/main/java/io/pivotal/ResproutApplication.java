package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ResproutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResproutApplication.class, args);
    } {}


    @Bean
    public FileSystem providesFileSystem() {
        return FileSystems.getDefault();
    }

    @Bean
    public YamlMessageConverter yamlMessageConverter() {
        YamlMessageConverter jsonConverter = new YamlMessageConverter();
        return jsonConverter;
    }



//    @Bean
//    public RepositoryRestConfigurer repositoryRestConfigurer() {
//
//        return new RepositoryRestConfigurerAdapter() {
//
//            @Override
//            public void configureHttpMessageConverters(
//                    List<HttpMessageConverter<?>> messageConverters) {
//                messageConverters.add(0, new YamlMessageConverter());
//            }
//
//        };
//    }
//
//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void configureMessageConverters(
//                    List<HttpMessageConverter<?>> messageConverters) {
//                messageConverters.add(0, new YamlMessageConverter());
//            }
//        };
//    }

}
