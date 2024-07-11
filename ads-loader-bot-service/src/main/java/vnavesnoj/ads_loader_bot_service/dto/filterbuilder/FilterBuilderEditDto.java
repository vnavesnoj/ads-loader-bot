package vnavesnoj.ads_loader_bot_service.dto.filterbuilder;

import lombok.NonNull;
import lombok.Value;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Value
public class FilterBuilderEditDto {

    String pattern;

    String currentInput;

    @NonNull
    Integer spotId;
}
