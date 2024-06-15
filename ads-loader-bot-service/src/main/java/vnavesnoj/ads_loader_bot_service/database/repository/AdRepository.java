package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Ad;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface AdRepository extends JpaRepository<Ad, Long> {

    Optional<Ad> findByUrl(String url);
}
