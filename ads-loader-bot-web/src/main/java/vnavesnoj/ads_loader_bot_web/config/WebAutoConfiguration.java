package vnavesnoj.ads_loader_bot_web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vnavesnoj.ads_loader_bot_service.config.ServiceAutoConfiguration;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Configuration
@Import(ServiceAutoConfiguration.class)
@ComponentScan(basePackages = "vnavesnoj.ads_loader_bot_web")
public class WebAutoConfiguration {
}
