package vnavesnoj.ads_loader_bot_service.service.component;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.service.component.impl.Html;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public interface Connector {

    Html getHtml(String href);
}
