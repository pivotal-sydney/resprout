package io.pivotal.web;


import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import io.pivotal.service.PackageService;
import io.pivotal.task.PackageTask;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    @Autowired
    private HomebrewPackageService homebrewPackageService;

    @Autowired
    private PackageRepository packageRepository;

    @RequestMapping("/find_packages")
    public ResponseEntity<?> findPackages() throws InterruptedException, IOException, GitAPIException {
        List<? extends PackageService> packageServices = Arrays.asList(homebrewPackageService);
        PackageTask.create(packageServices, packageRepository);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}