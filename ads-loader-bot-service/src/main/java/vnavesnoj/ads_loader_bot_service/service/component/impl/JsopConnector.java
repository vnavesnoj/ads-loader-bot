package vnavesnoj.ads_loader_bot_service.service.component.impl;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.service.component.Connector;

import java.time.LocalDateTime;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class JsopConnector implements Connector {

    public static final int MAX_BODY_SIZE = 5_000_000;

    @SneakyThrows
    @Override
    public Html getHtml(String href) {
        final var htmlString = Jsoup.connect(href)
                .maxBodySize(MAX_BODY_SIZE)
                .get()
                .body()
                .html();
        return new Html(href, htmlString, LocalDateTime.now());
    }
}
