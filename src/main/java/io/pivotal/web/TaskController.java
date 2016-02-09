package io.pivotal.web;


import com.fasterxml.jackson.annotation.JacksonInject;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import io.pivotal.service.PackageService;
import io.pivotal.task.PackageTask;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    HomebrewPackageService homebrewPackageService;

    @Autowired
    private PackageRepository packageRepository;

    @RequestMapping("/find_packages")
    public String findPackages() throws InterruptedException, IOException, GitAPIException {


        List<? extends PackageService> packageServices = Arrays.asList(homebrewPackageService);

        PackageTask.create(packageServices, packageRepository);
        return "";
    }
}