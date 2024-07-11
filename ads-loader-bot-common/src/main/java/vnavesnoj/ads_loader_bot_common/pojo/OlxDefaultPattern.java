package vnavesnoj.ads_loader_bot_common.pojo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import vnavesnoj.ads_loader_bot_common.annotation.MaxLengthOfElements;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;

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
}
