package vnavesnoj.ads_loader_bot_service.service.component;

import vnavesnoj.ads_loader_bot_common.database.entity.Spot;

import java.util.List;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface SpotDownloader<A> {

    List<A> download(Spot spot);
}
