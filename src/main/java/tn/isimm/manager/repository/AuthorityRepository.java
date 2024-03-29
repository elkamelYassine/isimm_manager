package tn.isimm.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimm.manager.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
