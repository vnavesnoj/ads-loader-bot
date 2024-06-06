package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
public class OlxDefaultAdsJson {

    private final String json;

    private final String href;

    private final LocalDateTime connectionTime;
}
