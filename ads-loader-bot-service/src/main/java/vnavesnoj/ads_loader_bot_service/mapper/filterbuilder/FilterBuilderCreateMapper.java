package vnavesnoj.ads_loader_bot_service.mapper.filterbuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.FilterBuilder;
import vnavesnoj.ads_loader_bot_service.database.repository.SpotRepository;
import vnavesnoj.ads_loader_bot_service.database.repository.UserRepository;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.exception.PatternCastException;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;
import vnavesnoj.ads_loader_bot_service.exception.UserNotExistsException;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class FilterBuilderCreateMapper implements Mapper<FilterBuilderCreateDto, FilterBuilder> {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JsonProcessingException.class)
    @Override
    public FilterBuilder map(FilterBuilderCreateDto filterBuilder) {
        final var user = userRepository.findById(filterBuilder.getUserId())
                .orElseThrow(() ->
                        new UserNotExistsException("user with id = " + filterBuilder.getUserId() + " does not exist"));
        final var spot = spotRepository.findById(filterBuilder.getSpotId())
                .orElseThrow(() ->
                        new SpotNotExistsException("spot with id = " + filterBuilder.getSpotId() + " does not exist"));
        if (!spot.getAnalyzer().getPatternClass().isInstance(filterBuilder.getPattern())) {
            throw new PatternCastException("pattern is not instance of " + spot.getAnalyzer().getPatternClass());
        }
        return FilterBuilder.builder()
                .pattern(objectMapper.writeValueAsString(filterBuilder.getPattern()))
                .instant(Instant.now())
                .spot(spot)
                .user(user)
                .build();
    }
}
