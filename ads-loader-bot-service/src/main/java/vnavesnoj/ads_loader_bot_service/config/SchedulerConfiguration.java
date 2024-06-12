package vnavesnoj.ads_loader_bot_service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "app.scheduler.enabled", matchIfMissing = true)
public class SchedulerConfiguration {
}
