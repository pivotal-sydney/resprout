package io.pivotal.service;

import io.pivotal.model.Package;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;


public interface PackageService {
    void update(URI remotePath, Path localDirectory) throws IOException, GitAPIException;
    List<Package> find(Path localPath) throws IOException;
}