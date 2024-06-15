package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterRepository extends JpaRepository<Filter, Long> {

    @EntityGraph(attributePaths = "user")
    Page<Filter> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<Filter> findByNameAndUserId(String name, Long userId);
}
