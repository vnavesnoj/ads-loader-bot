package vnavesnoj.ads_loader_bot_web.assistant.component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_web.mapper.*;

import java.util.Arrays;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultPatternValueMapper implements PatternValueMapper {

    private final WordPatternMapper wordPatternMapper;
    private final PriceTypeMapper priceTypeMapper;
    private final PriceMapper priceMapper;
    private final CurrencyCodeMapper currencyCodeMapper;

    @PostConstruct
    @Override
    public void checkFields() {
        Arrays.stream(OlxDefaultPattern.class.getDeclaredFields())
                .forEach(field -> getMapper(field.getName()));
    }

    @Override
    public Object map(String field, String value) {
        return this.getMapper(field).map(value);
    }

    @Override
    public StringValueMapper<?> getMapper(String field) {
        return switch (field) {
            case OlxDefaultPattern.Fields.descriptionPatterns,
                    OlxDefaultPattern.Fields.cityNames,
                    OlxDefaultPattern.Fields.regionNames -> wordPatternMapper;
            case OlxDefaultPattern.Fields.minPrice,
                    OlxDefaultPattern.Fields.maxPrice -> priceMapper;
            case OlxDefaultPattern.Fields.priceType -> priceTypeMapper;
            case OlxDefaultPattern.Fields.currencyCode -> currencyCodeMapper;
            default ->
                    throw new UnknownInputFieldException("there is not PatternValueMapper for field '%s'".formatted(field));
        };
    }
}
