package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class OlxDefaultAdBody {

    @NonNull
    Long id;

    @NonNull
    String title;

    @NonNull
    String description;

    @NonNull
    String url;

    @NonNull
    LocalDateTime createdTime;

    LocalDateTime pushupTime;

    LocalDateTime lastRefreshTime;

    Boolean negotiable;

    Boolean free;

    Boolean exchange;

    Long regularPrice;

    String currencyCode;

    String cityName;

    String regionName;
}
