package vnavesnoj.ads_loader_bot_common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.context.MessageSource;
import vnavesnoj.ads_loader_bot_common.annotation.MaxLengthOfElements;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OlxDefaultPattern {

    @Size(max = 5, message = "{bot.validation.constraint.Size.descriptionPatterns.message}")
    @MaxLengthOfElements(value = 16, message = "{bot.validation.constraint.MaxLengthOfElements.descriptionPatterns.message}")
    String[] descriptionPatterns;

    @NotNull(message = "{bot.validation.constraint.NotNull.priceType.message}")
    PriceType priceType;

    @Min(value = 0, message = "{bot.validation.constraint.Min.minPrice.message}")
    Long minPrice;

    @Max(value = Long.MAX_VALUE, message = "{bot.validation.constraint.Max.maxPrice.message}")
    Long maxPrice;

    CurrencyCode currencyCode;

    @Size(max = 5, message = "{bot.validation.constraint.Size.cityNames.message}")
    @MaxLengthOfElements(value = 32, message = "{bot.validation.constraint.MaxLengthOfElements.cityNames.message}")
    String[] cityNames;

    @Size(max = 5, message = "{bot.validation.constraint.Size.regionNames.message}")
    @MaxLengthOfElements(value = 32, message = "{bot.validation.constraint.MaxLengthOfElements.regionNames.message}")
    String[] regionNames;

    @NonNull
    @JsonIgnore
    public String getDescriptionPatterns(MessageSource messageSource, Locale locale) {
        return descriptionPatterns == null || descriptionPatterns.length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(descriptionPatterns);
    }

    @NonNull
    @JsonIgnore
    public String getPriceType(MessageSource messageSource, Locale locale) {
        return priceType == null
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : messageSource.getMessage(priceType.getMessageSource(), null, locale);
    }

    @NonNull
    @JsonIgnore
    public String getCurrencyCode(MessageSource messageSource, Locale locale) {
        return currencyCode == null
                ? messageSource.getMessage("bot.filter.info.currency-code", null, locale)
                : currencyCode.name();
    }

    @NonNull
    @JsonIgnore
    public String getCurrencyCode(String defaultMessage) {
        return currencyCode == null
                ? defaultMessage
                : currencyCode.name();
    }

    @NonNull
    @JsonIgnore
    public String getMinPriceString() {
        return minPrice == null ? "0" : minPrice.toString();
    }

    @NonNull
    @JsonIgnore
    public String getMaxPriceString(MessageSource messageSource, Locale locale) {
        return maxPrice == null
                ? messageSource.getMessage("bot.filter.info.max", null, locale)
                : maxPrice.toString();
    }

    @NonNull
    public String getCityNames(MessageSource messageSource, Locale locale) {
        return cityNames == null || cityNames.length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(cityNames);
    }

    @NonNull
    public String getRegionNames(MessageSource messageSource, Locale locale) {
        return regionNames == null || regionNames.length == 0
                ? messageSource.getMessage("bot.filter.info.not-indicated", null, locale)
                : Arrays.toString(regionNames);
    }
}
