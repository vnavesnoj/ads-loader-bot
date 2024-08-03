package vnavesnoj.ads_loader_bot_service.mapper.ad;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.Platform;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Ad;
import vnavesnoj.ads_loader_bot_persistence.database.entity.AdBody;

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
                .platform(Platform.OLXUA)
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
