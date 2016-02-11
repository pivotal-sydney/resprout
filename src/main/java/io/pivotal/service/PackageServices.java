package io.pivotal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

public class PackageServices {

    @Autowired
    private HomebrewPackageService homebrewPackageService;

    @Bean
    public List<PackageService> services() {
        return Arrays.asList(homebrewPackageService);
    }
}
