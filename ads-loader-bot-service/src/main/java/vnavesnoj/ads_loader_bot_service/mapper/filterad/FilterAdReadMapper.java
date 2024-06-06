package vnavesnoj.ads_loader_bot_service.mapper.filterad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Ad;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_common.database.entity.FilterAd;
import vnavesnoj.ads_loader_bot_service.dto.ad.AdReadDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterMetaReadDto;
import vnavesnoj.ads_loader_bot_service.dto.filterad.FilterAdReadDto;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterAdReadMapper implements Mapper<FilterAd, FilterAdReadDto> {

    private final Mapper<Filter, FilterMetaReadDto> filterMetaReadMapper;
    private final Mapper<Ad, AdReadDto> adReadMapper;

    @Override
    public FilterAdReadDto map(FilterAd object) {
        return new FilterAdReadDto(
                object.getId(),
                filterMetaReadMapper.map(object.getFilter()),
                adReadMapper.map(object.getAd())
        );
    }
}
