package vnavesnoj.ads_loader_bot_web.assistant.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultFilterBuilderManager implements FilterBuilderManager {

    private final FilterBuilderService filterBuilderService;

    private final OlxDefaultPattern DEFAULT_PATTERN = OlxDefaultPattern.builder()
            .priceType(PriceType.ALL)
            .minPrice(0L)
            .currencyCode(CurrencyCode.UAH)
            .build();

    @Override
    public FilterBuilderReadDto createNewFilterBuilder(Long userId, Integer spotId) {
        return Optional.of(DEFAULT_PATTERN)
                .map(pattern -> new FilterBuilderCreateDto(pattern, spotId, userId))
                .map(filterBuilderService::create)
                .orElseThrow();
    }

    @Override
    public Optional<FilterBuilderReadDto> updatePatternField(FilterBuilderReadDto filterBuilder, String value) {
        return Optional.empty();
    }

    @Override
    public Optional<FilterBuilderReadDto> updatePatternField(Long filterBuilderId, String patternField, String value) {
        return Optional.empty();
    }

    @Override
    public Optional<FilterBuilderReadDto> resetPatternField(FilterBuilderReadDto filterBuilder, String field) {
        return Optional.empty();
    }
}
