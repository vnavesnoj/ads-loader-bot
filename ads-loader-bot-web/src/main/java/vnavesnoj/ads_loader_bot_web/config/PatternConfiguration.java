package vnavesnoj.ads_loader_bot_web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vnavesnoj.ads_loader_bot_web.mapper.StringValueMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Configuration
public class PatternConfiguration {

    @Bean
    Map<Class<?>, StringValueMapper<?>> mapOfValueMappers(ApplicationContext context) {
        final var map = new HashMap<Class<?>, StringValueMapper<?>>();
        context.getBeansOfType(StringValueMapper.class).values()
                .forEach(item -> map.put(item.getClass(), item));
        return Map.copyOf(map);
    }
}
