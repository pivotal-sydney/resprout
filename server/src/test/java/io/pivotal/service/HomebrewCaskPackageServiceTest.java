package io.pivotal.service;

import com.google.common.jimfs.Jimfs;
import io.pivotal.model.Package;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomebrewCaskPackageServiceTest {

    private HomebrewCaskPackageService homebrewCaskPackageService;

    private FileSystem fileSystem;
    private GitService gitService;

    private Path localPath;
    private URI remotePath;
    private Path formulaPath;

    @Before
    public void before() throws Exception {
        fileSystem = Jimfs.newFileSystem(com.google.common.jimfs.Configuration.unix());
        gitService = new GitService();
        localPath = fileSystem.getPath("/tmp/localPath");
        remotePath = localPath.toUri();

        homebrewCaskPackageService = new HomebrewCaskPackageService(fileSystem, gitService, localPath, remotePath);

        formulaPath = localPath.resolve(fileSystem.getPath(HomebrewCaskPackageService.REPOSITORY_NAME, "Casks"));

        Files.createDirectories(formulaPath);
    }

    @Test
    public void whenNameIsDifferent_findPackagesTest() throws IOException {
        createCaskRecipe("carrot_cake.rb", "Carrot Cake");
        List<Package> packages = homebrewCaskPackageService.find();
        assertThat(packages.size()).isEqualTo(1);
        assertThat(packages.get(0).getName()).isEqualTo("carrot_cake");
        assertThat(packages.get(0).getDescription()).isEqualTo("Carrot Cake");

    }

    @Test
    public void whenNameIsTheSame_findPackagesTest() throws Exception {
        createCaskRecipe("someapp.rb", "someapp");
        List<Package> packages = homebrewCaskPackageService.find();
        assertThat(packages.get(0).getName()).isEqualTo("someapp");
        assertThat(packages.get(0).getDescription()).isNull();

    }

    @Test
    public void whenFileIsNotRB_ingore() throws Exception {
        createCaskRecipe("something.yml", "something");
        List<Package> packages = homebrewCaskPackageService.find();
        assertThat(packages.size()).isEqualTo(0);
    }

    private void createCaskRecipe(String fileName, String caskName) throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(formulaPath.resolve(fileName));
        bufferedWriter.write(String.format("name \"%s\"", caskName));
        bufferedWriter.close();
    }
}