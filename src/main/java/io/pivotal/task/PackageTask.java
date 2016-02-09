package io.pivotal.task;

import io.pivotal.model.Package;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.PackageService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class PackageTask {
    private static final Logger logger = LoggerFactory.getLogger(PackageTask.class);

    public static void create(List<? extends PackageService> services, PackageRepository packageRepository) throws InterruptedException, IOException, GitAPIException {

        List<Package> packages = new ArrayList<>();

        for (PackageService service :
                services) {
            Path localPath = Paths.get(System.getProperty("java.io.tmpdir"), "io.pivotal.resprout", "package_services");
            Files.createDirectories(localPath);

            service.update(new File("/usr/local/.git").toURI(), localPath);
            packages.addAll(service.find(localPath));
        }

        packageRepository.save(packages);

        logger.info("{} packages loaded", packages.size());
    }
}
