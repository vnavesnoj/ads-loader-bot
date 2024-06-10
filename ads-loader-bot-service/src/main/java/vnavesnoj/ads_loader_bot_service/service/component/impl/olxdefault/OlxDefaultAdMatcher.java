package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.component.AdMatcher;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultPattern;

import static org.apache.commons.lang3.StringUtils.containsAnyIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultAdMatcher implements AdMatcher<OlxDefaultAdBody> {

    private final Mapper<Filter, OlxDefaultPattern> filterPatternMapper;

    @Override
    public boolean match(OlxDefaultAdBody adObject, Filter filter) {
        if (!filter.isEnabled()) {
            return false;
        }
        final var pattern = filterPatternMapper.map(filter);
        long minPrice = pattern.getMinPrice() == null ? -1 : pattern.getMinPrice();
        long maxPrice = pattern.getMaxPrice() == null ? -1 : pattern.getMaxPrice();
        long regularPrice = adObject.getRegularPrice() == null ? 0 : adObject.getRegularPrice();
        final var priceTypeMatch = pattern.getPriceType() == null
                || switch (pattern.getPriceType()) {
            case FREE -> adObject.getFree();
            case EXCHANGE -> adObject.getExchange();
            default -> true;
        };
        return (containsAnyIgnoreCase(adObject.getTitle(), pattern.getDescriptionPatterns()) ||
                containsAnyIgnoreCase(adObject.getDescription(), pattern.getDescriptionPatterns()))
                && priceTypeMatch
                && (pattern.getCurrencyCode() == null ||
                equalsIgnoreCase(adObject.getCurrencyCode(), pattern.getCurrencyCode().name()))
                && (minPrice == -1 || minPrice <= regularPrice)
                && (maxPrice == -1 || regularPrice <= maxPrice)
                && (pattern.getCityNames() == null ||
                pattern.getCityNames().length == 0 ||
                containsAnyIgnoreCase(adObject.getCityName(), pattern.getCityNames()))
                && (pattern.getRegionNames() == null ||
                pattern.getRegionNames().length == 0 ||
                containsAnyIgnoreCase(adObject.getRegionName(), pattern.getRegionNames()));
    }
}
