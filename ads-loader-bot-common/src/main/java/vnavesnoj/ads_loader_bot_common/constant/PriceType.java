package vnavesnoj.ads_loader_bot_common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Getter
public enum PriceType {

    FREE("enum.price-type.free"),
    EXCHANGE("enum.price-type.exchange"),
    PAID("enum.price-type.paid"),
    ALL("enum.price-type.all");

    private final String messageSource;
}
