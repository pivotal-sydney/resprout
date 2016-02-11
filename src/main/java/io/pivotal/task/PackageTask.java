package io.pivotal.task;

import io.pivotal.model.Package;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import io.pivotal.service.PackageService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Profile("production-worker")
    @Scheduled(cron = "${resprout.repositories.cron}")
    public void scheduledCreate() throws InterruptedException, GitAPIException, IOException {
        create(Arrays.asList(homebrewPackageService), packageRepository);
    }

    public static void create(List<? extends PackageService> services, PackageRepository packageRepository) throws InterruptedException, IOException, GitAPIException {

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
