package io.pivotal.persistence;


import io.pivotal.model.HomebrewPackage;
import io.pivotal.model.Package;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "homebrew_packages", path = "homebrew_packages")
public interface HomebrewPackageRepository extends PagingAndSortingRepository<HomebrewPackage, Long> {
}
