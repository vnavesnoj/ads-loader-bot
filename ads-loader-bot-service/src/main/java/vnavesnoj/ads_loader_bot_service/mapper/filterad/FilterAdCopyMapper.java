package vnavesnoj.ads_loader_bot_service.mapper.filterad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Ad;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterAd;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterAdCopyMapper implements Mapper<FilterAd, FilterAd> {

    private final Mapper<Ad, Ad> adCopyMapper;

    @Override
    public FilterAd map(FilterAd object) {
        return object;
    }

    @Override
    public FilterAd map(FilterAd from, FilterAd to) {
        adCopyMapper.map(from.getAd(), to.getAd());
        return to;
    }
}
