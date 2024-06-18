package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Category;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
