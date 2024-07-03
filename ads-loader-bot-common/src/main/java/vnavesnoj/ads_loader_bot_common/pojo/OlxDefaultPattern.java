package vnavesnoj.ads_loader_bot_common.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
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

    String[] descriptionPatterns;

    PriceType priceType;

    Long minPrice;

    Long maxPrice;

    CurrencyCode currencyCode;

    String[] cityNames;

    String[] regionNames;
}
