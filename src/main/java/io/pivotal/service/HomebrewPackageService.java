package io.pivotal.service;

import io.pivotal.model.HomebrewPackage;
import io.pivotal.model.Package;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HomebrewPackageService implements PackageService {

    static final String REPOSITORY_NAME = "homebrew";

    private final FileSystem fileSystem;
    private final GitService gitService;

    @Autowired
    public HomebrewPackageService(FileSystem fileSystem, GitService gitService) {
        this.fileSystem = fileSystem;
        this.gitService = gitService;
    }

    private static final Logger logger = LoggerFactory.getLogger(HomebrewPackageService.class);

    @Override
    public void update(URI remotePath, Path localDirectory) throws IOException, GitAPIException {
        gitService.createOrUpdate(remotePath, iceCream(localDirectory).toFile());
    }

    @Override
    public List<Package> find(Path localPath) throws IOException {
        logger.info("reading packages from '{}'", iceCream(localPath));

        Path formulaPath = iceCream(localPath).resolve(fileSystem.getPath("Library", "Formula"));

            return Files.list(formulaPath)
                    .parallel()
                    .map(f -> {
                        try {
                            return getPackage(f);
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
    }

    private Package getPackage(Path path) throws IOException {
        String name = path.getFileName().toString().replaceAll("\\..*$", "");
        return new HomebrewPackage(name, getDescription(path));
    }

    private String getDescription(Path path) throws IOException {
        Pattern compile = Pattern.compile("^\\s*desc\\s+\"(?<description>.*)\"");
        List<String> lines = Files.readAllLines(path);

        Optional<Matcher> matcher = lines.stream()
                .map(l -> compile.matcher(l))
                .filter(Matcher::matches)
                .findAny();

        return matcher.isPresent() ? matcher.get().group("description") : "no description found";
    }

    private Path iceCream(Path path) {
        return path.resolve(REPOSITORY_NAME);
    }
}

