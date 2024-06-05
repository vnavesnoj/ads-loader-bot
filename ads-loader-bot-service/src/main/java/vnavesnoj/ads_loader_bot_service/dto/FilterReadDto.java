package vnavesnoj.ads_loader_bot_service.dto;

import lombok.Value;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterReadDto {

    FilterMetaReadDto meta;

    String jsonPattern;
}
