package vnavesnoj.ads_loader_bot_common.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class OlxDefaultPattern {

    String[] descriptionPatterns;

    PriceType priceType;

    Long minPrice;

    Long maxPrice;

    CurrencyCode currencyCode;

    String[] cityNames;

    String[] regionNames;
}
