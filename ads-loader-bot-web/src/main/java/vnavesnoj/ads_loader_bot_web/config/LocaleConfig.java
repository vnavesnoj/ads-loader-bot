package vnavesnoj.ads_loader_bot_web.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Log4j2
@Configuration
public class LocaleConfig {

    private final Locale defaultLocale;

    LocaleConfig(@Value("${spring.application.locale.default:en}") String defaultLocale) {
        this.defaultLocale = Locale.of(defaultLocale);
    }

    @PostConstruct
    void init() {
        LocaleContextHolder.setDefaultLocale(defaultLocale);
        log.info("the default application locale is set to " + defaultLocale.getLanguage());
    }
}
