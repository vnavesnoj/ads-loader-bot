package vnavesnoj.ads_loader_bot_service.mapper.ad;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Ad;
import vnavesnoj.ads_loader_bot_common.database.entity.AdBody;
import vnavesnoj.ads_loader_bot_common.database.entity.Platform;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class OlxDefaultAdMapper implements Mapper<OlxDefaultAdBody, Ad> {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @SneakyThrows
    @Override
    public Ad map(OlxDefaultAdBody object) {
        final var jsonBody = objectMapper.writeValueAsString(object);
        final var ad = Ad.builder()
                .id(object.getId())
                .platform(Platform.OLX)
                .url(object.getUrl())
                .title(object.getTitle())
                .instant(Instant.now())
                .pushupTime(object.getPushupTime() != null
                        ? object.getPushupTime().toInstant()
                        : object.getCreatedTime().toInstant())
                .hash(jsonBody.hashCode())
                .build();
        ad.setAdBody(new AdBody(ad.getId(), jsonBody, ad));
        return ad;
    }
}
