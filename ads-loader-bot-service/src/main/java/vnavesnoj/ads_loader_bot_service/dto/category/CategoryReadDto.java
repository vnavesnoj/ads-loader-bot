package vnavesnoj.ads_loader_bot_service.dto.category;

import lombok.Value;
import vnavesnoj.ads_loader_bot_common.constant.Platform;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class CategoryReadDto {

    Integer id;

    Platform platform;

    String name;
}
