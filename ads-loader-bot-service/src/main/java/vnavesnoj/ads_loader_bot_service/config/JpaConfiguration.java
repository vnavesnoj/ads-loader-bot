package vnavesnoj.ads_loader_bot_service.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Configuration
@EnableJpaRepositories(basePackages = "vnavesnoj.ads_loader_bot_service.database.repository")
@EntityScan(basePackages = "vnavesnoj/ads_loader_bot_persistence/database/entity")
public class JpaConfiguration {
}
