package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.component.AdMatcher;

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
        return checkDescriptionPatterns(adObject, pattern)
                && checkPriceType(adObject, pattern)
                && checkCurrencyCode(adObject, pattern)
                && checkRegularPrice(adObject, pattern)
                && checkCityNames(adObject, pattern)
                && checkRegionNames(adObject, pattern);
    }

    private static boolean checkRegionNames(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        return pattern.getRegionNames() == null ||
                pattern.getRegionNames().length == 0 ||
                containsAnyIgnoreCase(adObject.getRegionName(), pattern.getRegionNames());
    }

    private static boolean checkCityNames(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        return pattern.getCityNames() == null ||
                pattern.getCityNames().length == 0 ||
                containsAnyIgnoreCase(adObject.getCityName(), pattern.getCityNames());
    }

    private static boolean checkRegularPrice(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        long regularPrice = adObject.getRegularPrice() == null ? 0 : adObject.getRegularPrice();
        long minPrice = pattern.getMinPrice() == null ? -1 : pattern.getMinPrice();
        long maxPrice = pattern.getMaxPrice() == null ? -1 : pattern.getMaxPrice();
        return (minPrice == -1 || minPrice <= regularPrice)
                && (maxPrice == -1 || regularPrice <= maxPrice);
    }

    private static boolean checkCurrencyCode(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        return pattern.getCurrencyCode() == null ||
                equalsIgnoreCase(adObject.getCurrencyCode(), pattern.getCurrencyCode().name());
    }

    private static boolean checkPriceType(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        return pattern.getPriceType() == null
                || switch (pattern.getPriceType()) {
            case FREE -> adObject.getFree();
            case EXCHANGE -> adObject.getExchange();
            default -> true;
        };
    }

    private static boolean checkDescriptionPatterns(OlxDefaultAdBody adObject, OlxDefaultPattern pattern) {
        if (pattern.getDescriptionPatterns() == null || pattern.getDescriptionPatterns().length == 0) {
            return true;
        }
        return containsAnyIgnoreCase(adObject.getTitle(), pattern.getDescriptionPatterns()) ||
                containsAnyIgnoreCase(adObject.getDescription(), pattern.getDescriptionPatterns());
    }
}
