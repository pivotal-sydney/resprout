package io.pivotal.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.URIish;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;

@Service
public class GitService {
    public void createOrUpdate(URI remotePath, File directory) throws IOException, GitAPIException {
        try {
            Git git = Git.open(directory);
            git.fetch();
            git.reset().setMode(ResetCommand.ResetType.HARD)
            .setRef("origin/master").call();
        } catch (RepositoryNotFoundException e) {
            Git.cloneRepository()
                    .setURI(remotePath.toString())
                    .setDirectory(directory).call();
        }
    }
}
