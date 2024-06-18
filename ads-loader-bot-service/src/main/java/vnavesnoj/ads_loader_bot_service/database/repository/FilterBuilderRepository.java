package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterBuilderRepository extends JpaRepository<FilterBuilder, Long> {
}
