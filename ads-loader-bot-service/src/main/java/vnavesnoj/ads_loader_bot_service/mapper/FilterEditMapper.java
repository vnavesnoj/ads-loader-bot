package vnavesnoj.ads_loader_bot_service.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.dto.FilterEditDto;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class FilterEditMapper implements Mapper<FilterEditDto, Filter> {

    @Override
    public Filter map(FilterEditDto object) {
        final var filter = new Filter();
        copy(object, filter);
        return filter;
    }

    @Override
    public Filter map(FilterEditDto from, Filter to) {
        copy(from, to);
        return to;
    }

    private void copy(FilterEditDto from, Filter to) {
        if (from.getName() != null) {
            to.setName(from.getName());
        }
        if (from.getEnabled() != null) {
            to.setEnabled(from.getEnabled());
        }
    }
}
