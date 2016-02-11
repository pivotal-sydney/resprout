package io.pivotal.web;


import io.pivotal.task.PackageTask;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class TaskController {

    private final PackageTask packageTask;

    @Autowired
    public TaskController(PackageTask packageTask) {
        this.packageTask = packageTask;
    }

    @RequestMapping("/find_packages")
    public ResponseEntity<?> findPackages() throws InterruptedException, IOException, GitAPIException {
        packageTask.execute();
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}