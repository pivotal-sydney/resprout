package io.pivotal.task;

import io.pivotal.model.Package;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import io.pivotal.service.PackageService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PackageTask {

    private static final Logger logger = LoggerFactory.getLogger(PackageTask.class);

    private final HomebrewPackageService homebrewPackageService;
    private final PackageRepository packageRepository;

    @Autowired
    public PackageTask(HomebrewPackageService homebrewPackageService, PackageRepository packageRepository) {
        this.homebrewPackageService = homebrewPackageService;
        this.packageRepository = packageRepository;
    }

    @Scheduled(cron = "${resprout.repositories.cron}")
    public void execute() throws InterruptedException, GitAPIException, IOException {
        List<HomebrewPackageService> services = Arrays.asList(homebrewPackageService);

        List<Package> packages = new ArrayList<>();

        for (PackageService service : services) {
            service.update();
            packages.addAll(service.find());
        }

        packageRepository.deleteAll();
        packageRepository.save(packages);

        logger.info("{} packages loaded", packages.size());
    }
}
