package vnavesnoj.ads_loader_bot_web.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.CurrencyCode;
import vnavesnoj.ads_loader_bot_web.exception.CurrencyCodeNotExistsException;

import java.util.Arrays;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class CurrencyCodeMapper implements StringValueMapper<CurrencyCode> {

    @Override
    public CurrencyCode map(String object) {
        return Arrays.stream(CurrencyCode.values())
                .filter(item -> item.name().equalsIgnoreCase(object.strip()))
                .findFirst()
                .orElseThrow(CurrencyCodeNotExistsException::new);
    }
}
