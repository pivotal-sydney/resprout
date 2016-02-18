package io.pivotal.persistence;


import io.pivotal.model.UserSelection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestResource(collectionResourceRel = "user_selections", path = "user_selections")
public interface UserSelectionRepository extends PagingAndSortingRepository<UserSelection, Long> {
}
