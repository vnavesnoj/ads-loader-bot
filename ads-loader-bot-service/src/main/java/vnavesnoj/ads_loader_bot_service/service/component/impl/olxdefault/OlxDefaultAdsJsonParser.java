package vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_service.service.component.AdsJsonParser;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultAdsJson;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Component
public class OlxDefaultAdsJsonParser implements AdsJsonParser<OlxDefaultAdBody, OlxDefaultAdsJson> {

    @Override
    public List<OlxDefaultAdBody> parse(OlxDefaultAdsJson adsJson) {
        final var jsonElement = JsonParser.parseString(adsJson.getJson());

        final var ads = jsonElement.getAsJsonObject()
                .getAsJsonObject("listing")
                .getAsJsonObject("listing")
                .getAsJsonArray("ads");

        List<OlxDefaultAdBody> productList = new ArrayList<>();

        for (JsonElement ad : ads) {
            final var jsonObject = ad.getAsJsonObject();

            final var jsonPrice = Optional.of(jsonObject.get("price"))
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .orElse(null);

            final var jsonRegularPrice = Optional.ofNullable(jsonPrice)
                    .map(value -> value.get("regularPrice"))
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .orElse(null);

            final var jsonLocation = Optional.of(jsonObject.get("location"))
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .orElse(null);

            final var productBuilder = OlxDefaultAdBody.builder()
                    .id(jsonObject.getAsJsonPrimitive("id").getAsLong())
                    .title(jsonObject.getAsJsonPrimitive("title").getAsString())
                    .description(jsonObject.getAsJsonPrimitive("description").getAsString().replace("<br />", ""))
                    .url(jsonObject.getAsJsonPrimitive("url").getAsString())
                    .createdTime(ZonedDateTime.parse(
                            jsonObject.getAsJsonPrimitive("createdTime").getAsString(),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                    .pushupTime(Optional.ofNullable(jsonObject.getAsJsonPrimitive("pushupTime"))
                            .map(JsonPrimitive::getAsString)
                            .map(value -> ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                            .orElse(null))
                    .lastRefreshTime(Optional.ofNullable(jsonObject.getAsJsonPrimitive("lastRefreshTime"))
                            .map(JsonPrimitive::getAsString)
                            .map(value -> ZonedDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                            .orElse(null));

            if (jsonPrice != null) {
                productBuilder
                        .free(Optional.ofNullable(jsonPrice.getAsJsonPrimitive("free"))
                                .map(JsonPrimitive::getAsBoolean)
                                .orElse(null))
                        .exchange(Optional.ofNullable(jsonPrice.getAsJsonPrimitive("exchange"))
                                .map(JsonPrimitive::getAsBoolean)
                                .orElse(null));
            }

            if (jsonRegularPrice != null) {
                productBuilder
                        .negotiable(Optional.ofNullable(jsonRegularPrice.getAsJsonPrimitive("negotiable"))
                                .map(JsonPrimitive::getAsBoolean)
                                .orElse(null))
                        .regularPrice(Optional.ofNullable(jsonRegularPrice.getAsJsonPrimitive("value"))
                                .map(JsonPrimitive::getAsLong)
                                .orElse(null))
                        .currencyCode(Optional.ofNullable(jsonRegularPrice.getAsJsonPrimitive("currencyCode"))
                                .map(JsonPrimitive::getAsString)
                                .orElse(null));
            }

            if (jsonLocation != null) {
                productBuilder
                        .cityName(Optional.ofNullable(jsonLocation.getAsJsonPrimitive("cityName"))
                                .map(JsonPrimitive::getAsString)
                                .orElse(null))
                        .regionName(Optional.ofNullable(jsonLocation.getAsJsonPrimitive("regionName"))
                                .map(JsonPrimitive::getAsString)
                                .orElse(null));
            }

            productList.add(productBuilder.build());
        }
        return productList;
    }
}
