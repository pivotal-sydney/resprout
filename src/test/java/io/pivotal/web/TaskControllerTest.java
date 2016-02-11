package io.pivotal.web;

import io.pivotal.task.PackageTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @Mock
    private PackageTask packageTask;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void shouldExecutePackageTask() throws Exception {
        taskController.findPackages();

        verify(packageTask).execute();
    }
}