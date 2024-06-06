package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnavesnoj.ads_loader_bot_common.database.entity.Spot;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface SpotRepository extends JpaRepository<Spot, Integer> {
}
