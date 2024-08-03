package vnavesnoj.ads_loader_bot_web.mapper;

import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_web.exception.PriceFormatException;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class PriceMapper implements StringValueMapper<Long> {

    @Override
    public Long map(String object) {
        long minPrice = 0;
        try {
            minPrice = Long.parseLong(object.strip());
        } catch (NumberFormatException e) {
            throw new PriceFormatException(e);
        }
        if (minPrice < 0) {
            throw new PriceFormatException("price must be not negative number");
        }
        return minPrice;
    }
}
