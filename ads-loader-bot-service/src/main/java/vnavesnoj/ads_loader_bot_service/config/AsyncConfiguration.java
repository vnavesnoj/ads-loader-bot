package vnavesnoj.ads_loader_bot_service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(name = "app.async.enabled", matchIfMissing = true)
public class AsyncConfiguration {
}
