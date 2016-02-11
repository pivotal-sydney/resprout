package io.pivotal.task;

import io.pivotal.model.HomebrewPackage;
import io.pivotal.model.Package;
import io.pivotal.persistence.PackageRepository;
import io.pivotal.service.HomebrewPackageService;
import io.pivotal.service.PackageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PackageTaskTest {

    @Mock
    private HomebrewPackageService homebrewPackageService;
    @Mock
    private PackageRepository packageRepository;

    private PackageTask packageTask;

    private List<PackageService> services;
    private List<Package> homebrewPackages;

    @Before
    public void before() throws Exception {
        services = Arrays.asList(homebrewPackageService);
        packageTask = new PackageTask(services, packageRepository);

        homebrewPackages = Arrays.asList(new HomebrewPackage("wget", "desc"));
        when(homebrewPackageService.find()).thenReturn(homebrewPackages);
    }

    @Test
    public void shouldSaveAllPackages() throws Exception {

        packageTask.execute();

        verify(packageRepository).save(homebrewPackages);
    }

    @Test
    public void shouldUpdateServiceBeforeCallingFind() throws Exception {

        packageTask.execute();

        InOrder inOrder = inOrder(homebrewPackageService);
        inOrder.verify(homebrewPackageService).update();
        inOrder.verify(homebrewPackageService).find();
    }

    @Test
    public void shouldDeleteAllPackagesBeforeSaving() throws Exception {

        packageTask.execute();

        InOrder inOrder = inOrder(packageRepository);
        inOrder.verify(packageRepository).deleteAll();
        inOrder.verify(packageRepository).save(homebrewPackages);
    }
}