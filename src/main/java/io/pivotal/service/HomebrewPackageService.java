package io.pivotal.service;

import io.pivotal.model.HomebrewPackage;
import io.pivotal.model.Package;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class HomebrewPackageService implements PackageService {

    private static final Logger logger = LoggerFactory.getLogger(HomebrewPackageService.class);
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^\\s*desc\\s+\"(?<description>.*)\"");

    static final String REPOSITORY_NAME = "homebrew";

    private final FileSystem fileSystem;
    private final GitService gitService;
    private final Path localPath;
    private final URI remotePath;

    @Autowired
    public HomebrewPackageService(FileSystem fileSystem, GitService gitService,
                  @Value("${resprout.repositories.local_path}") String localPath,
                  @Value("${resprout.repositories.homebrew.git_url}") String remotePath) throws IOException, URISyntaxException {
        this(fileSystem, gitService, Paths.get(System.getProperty("java.io.tmpdir"), localPath), new URI(remotePath));
    }

    HomebrewPackageService(FileSystem fileSystem, GitService gitService, Path localPath, URI remotePath) throws IOException {
        this.fileSystem = fileSystem;
        this.gitService = gitService;
        this.localPath = localPath.resolve(REPOSITORY_NAME);
        this.remotePath = remotePath;
        Files.createDirectories(localPath);
    }

    @Override
    public void update() throws IOException, GitAPIException {
        logger.info("updating repository at '{}' -> '{}'", remotePath, localPath);
        gitService.createOrUpdate(remotePath, localPath.toFile());
    }

    @Override
    public List<Package> find() throws IOException {
        logger.info("reading packages from '{}'", localPath);

        Path formulaPath = localPath.resolve(fileSystem.getPath("Library", "Formula"));

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
        List<String> lines = Files.readAllLines(path);

        Optional<Matcher> matcher = lines.stream()
                .map(DESCRIPTION_PATTERN::matcher)
                .filter(Matcher::matches)
                .findAny();

        return matcher.isPresent() ? matcher.get().group("description") : "no description found";
    }
}

