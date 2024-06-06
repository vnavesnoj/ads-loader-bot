package vnavesnoj.ads_loader_bot_service.dto.filterad;

import lombok.Value;
import vnavesnoj.ads_loader_bot_service.dto.ad.AdReadDto;
import vnavesnoj.ads_loader_bot_service.dto.filter.FilterMetaReadDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterAdReadDto {

    Long id;

    FilterMetaReadDto filter;

    AdReadDto ad;
}
