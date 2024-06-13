package vnavesnoj.ads_loader_bot_service.mapper.ad;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Ad;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class AdCopyMapper implements Mapper<Ad, Ad> {

    @Override
    public Ad map(Ad object) {
        return object;
    }

    @Override
    public Ad map(Ad from, Ad to) {
        to.setTitle(from.getTitle());
        to.setUrl(from.getUrl());
        to.setPushupTime(from.getPushupTime());
        to.setHash(from.getHash());
        to.getAdBody().setJsonBody(from.getAdBody().getJsonBody());
        return to;
    }
}