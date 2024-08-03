package vnavesnoj.ads_loader_bot_web.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_web.exception.PriceTypeNotExistsException;

import java.util.Arrays;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class PriceTypeMapper implements StringValueMapper<PriceType> {

    @Override
    public PriceType map(String object) {
        return Arrays.stream(PriceType.values())
                .filter(item -> item.name().equalsIgnoreCase(object.strip()))
                .findFirst()
                .orElseThrow(PriceTypeNotExistsException::new);
    }
}
