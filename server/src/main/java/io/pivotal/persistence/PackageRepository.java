package io.pivotal.persistence;


import io.pivotal.model.Package;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface PackageRepository extends PagingAndSortingRepository<Package, Long> {
    public List<Package> findByNameContainingIgnoreCase(@Param("name") String name);
}
