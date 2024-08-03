package vnavesnoj.ads_loader_bot_web.assistant.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_web.mapper.*;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultFilterBuilderManager implements FilterBuilderManager {

    private final FilterBuilderService filterBuilderService;
    private final WordPatternMapper wordPatternMapper;
    private final PriceTypeMapper priceTypeMapper;
    private final PriceMapper priceMapper;
    private final CurrencyCodeMapper currencyCodeMapper;

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


    //TODO возвращать String и выкидывать эксепшн в случае если нет по id
    @Override
    public Optional<FilterBuilderReadDto> updatePatternField(Long filterBuilderId, String patternField, String value) {
        final var parsedValue = parseInput(patternField, value);
        return filterBuilderService.updatePatternField(filterBuilderId, patternField, parsedValue);
    }

    @Override
    public Optional<FilterBuilderReadDto> resetPatternField(Long filterBuilderId, String field) {
        return filterBuilderService.updatePatternField(filterBuilderId, field, getDefaultValue(field));
    }

    private Object parseInput(String field, String input) {
        final StringValueMapper<?> mapper = getStringValueMapper(field);
        return mapper.map(input);
    }

    //TODO убого. Лучше уже работать с аннотациями
    private StringValueMapper<?> getStringValueMapper(String field) {
        return switch (field) {
            case OlxDefaultPattern.Fields.descriptionPatterns,
                    OlxDefaultPattern.Fields.cityNames,
                    OlxDefaultPattern.Fields.regionNames -> wordPatternMapper;
            case OlxDefaultPattern.Fields.priceType -> priceTypeMapper;
            case OlxDefaultPattern.Fields.minPrice, OlxDefaultPattern.Fields.maxPrice -> priceMapper;
            case OlxDefaultPattern.Fields.currencyCode -> currencyCodeMapper;
            default -> throw new UnknownInputFieldException("unknown input field = " + field);
        };
    }

    private Object getDefaultValue(String field) {
        return switch (field) {
            case OlxDefaultPattern.Fields.descriptionPatterns -> DEFAULT_PATTERN.getDescriptionPatterns();
            case OlxDefaultPattern.Fields.priceType -> DEFAULT_PATTERN.getPriceType();
            case OlxDefaultPattern.Fields.minPrice -> DEFAULT_PATTERN.getMinPrice();
            case OlxDefaultPattern.Fields.maxPrice -> DEFAULT_PATTERN.getMaxPrice();
            case OlxDefaultPattern.Fields.currencyCode -> DEFAULT_PATTERN.getCurrencyCode();
            case OlxDefaultPattern.Fields.cityNames -> DEFAULT_PATTERN.getCityNames();
            case OlxDefaultPattern.Fields.regionNames -> DEFAULT_PATTERN.getRegionNames();
            default -> throw new UnknownInputFieldException("unknown input field = " + field);
        };
    }
}
