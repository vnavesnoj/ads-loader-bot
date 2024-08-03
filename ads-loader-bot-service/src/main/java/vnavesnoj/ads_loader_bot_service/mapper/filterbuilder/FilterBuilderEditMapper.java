package vnavesnoj.ads_loader_bot_service.mapper.filterbuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.mapper.Mapper;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderEditDto;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterBuilderEditMapper implements Mapper<FilterBuilderEditDto, FilterBuilder> {

    private final SpotRepository spotRepository;
    private final ObjectMapper objectMapper;

    @Override
    public FilterBuilder map(FilterBuilderEditDto object) {
        final var filterBuilder = new FilterBuilder();
        copy(object, filterBuilder);
        return filterBuilder;
    }

    @Override
    public FilterBuilder map(FilterBuilderEditDto from, FilterBuilder to) {
        copy(from, to);
        return to;
    }

    @SneakyThrows(JsonProcessingException.class)
    private void copy(FilterBuilderEditDto from, FilterBuilder to) {
        final var pattern = objectMapper.writeValueAsString(from.getPattern());
        to.setPattern(pattern);
        to.setCurrentInput(from.getCurrentInput());
        if (to.getSpot() == null
                || to.getSpot().getId() == null
                || !to.getSpot().getId().equals(from.getSpotId())) {
            final var spot = spotRepository.findById(from.getSpotId())
                    .orElseThrow(() -> new SpotNotExistsException("spot with id = " + from.getSpotId() + " not exists"));
            to.setSpot(spot);
        }
    }
}
