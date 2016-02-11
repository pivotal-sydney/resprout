package io.pivotal.task;

import io.pivotal.model.HomebrewPackage;
import io.pivotal.model.Package;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PackageTaskTest {

    @Mock
    private HomebrewPackageService homebrewPackageService;
    @Mock
    private PackageRepository packageRepository;
    @InjectMocks
    private PackageTask packageTask;

    private List<Package> homebrewPackages = new LinkedList<>();

    @Before
    public void before() throws Exception {
        homebrewPackages.add(new HomebrewPackage("wget", "desc"));
        when(homebrewPackageService.find()).thenReturn(homebrewPackages);
    }

    @Test
    public void shouldSaveAllPackages() throws Exception {

        packageTask.scheduledCreate();

        verify(packageRepository).save(homebrewPackages);
    }

    @Test
    public void shouldUpdateServiceBeforeCallingFind() throws Exception {

        packageTask.scheduledCreate();

        InOrder inOrder = inOrder(homebrewPackageService);
        inOrder.verify(homebrewPackageService).update();
        inOrder.verify(homebrewPackageService).find();
    }

    @Test
    public void shouldDeleteAllPackagesBeforeSaving() throws Exception {

        packageTask.scheduledCreate();

        InOrder inOrder = inOrder(packageRepository);
        inOrder.verify(packageRepository).deleteAll();
        inOrder.verify(packageRepository).save(homebrewPackages);
    }
}