package io.pivotal.persistence;


import io.pivotal.model.HomebrewCaskPackage;
import io.pivotal.model.HomebrewPackage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "homebrew_cask_packages", path = "homebrew_cask_packages")
public interface HomebrewCaskPackageRepository extends PagingAndSortingRepository<HomebrewCaskPackage, Long> {
}
