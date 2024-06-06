package vnavesnoj.ads_loader_bot_service.dto.filter;

import lombok.Value;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterCreateDto {

    String name;

    Integer spotId;

    String jsonPattern;

    Long userId;
}
