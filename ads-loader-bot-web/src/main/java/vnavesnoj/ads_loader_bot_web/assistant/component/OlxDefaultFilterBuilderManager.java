package vnavesnoj.ads_loader_bot_web.assistant.component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderReadDto;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;
import vnavesnoj.ads_loader_bot_web.exception.FilterBuilderNotFoundException;

import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultFilterBuilderManager implements FilterBuilderManager {

    private final FilterBuilderService filterBuilderService;

    private final PatternValueMapper olxDefaultPatternValueMapper;

    //TODO need immutable copy
    private final OlxDefaultPattern DEFAULT_PATTERN = OlxDefaultPattern.builder()
            .priceType(PriceType.ALL)
            .minPrice(0L)
            .currencyCode(CurrencyCode.UAH)
            .build();

    private final Class<? extends OlxDefaultPattern> DEFAULT_PATTERN_CLASS = DEFAULT_PATTERN.getClass();

    @Override
    public FilterBuilderReadDto create(Integer spotId, Long userId) {
        return Optional.of(DEFAULT_PATTERN)
                .map(pattern -> new FilterBuilderCreateDto(pattern, spotId, userId))
                .map(filterBuilderService::create)
                .orElseThrow();
    }

    @Override
    public String updateField(Long filterBuilderId, @NonNull String field, String value) {
        return Optional.of(field)
                .map(item -> olxDefaultPatternValueMapper.map(field, value))
                .map(item -> filterBuilderService.updatePatternField(filterBuilderId, field, item))
                .map(item -> value)
                .orElseThrow();
    }

    @SneakyThrows
    @Override
    public String resetField(Long filterBuilderId, @NonNull String field) {
        try {
            final var declaredField = DEFAULT_PATTERN_CLASS.getDeclaredField(field);
            try {
                declaredField.setAccessible(true);
                final var defaultValue = declaredField.get(DEFAULT_PATTERN);
                filterBuilderService.updatePatternField(filterBuilderId, field, defaultValue)
                        .orElseThrow(FilterBuilderNotFoundException::new);
                return defaultValue != null ? defaultValue.toString() : "";
            } finally {
                declaredField.setAccessible(false);
            }
        } catch (NoSuchFieldException e) {
            throw new UnknownInputFieldException("field '%s' not exists in %s".formatted(field, DEFAULT_PATTERN_CLASS.getName()));
        }
    }
}
