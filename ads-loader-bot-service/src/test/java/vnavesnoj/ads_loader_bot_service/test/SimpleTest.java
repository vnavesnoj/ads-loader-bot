package vnavesnoj.ads_loader_bot_service.test;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.constant.PriceType;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.annotation.IT;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@IT
public class SimpleTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void testing() {
        final Class<?> patternClass = AnalyzerEnum.OLX_UA_DEFAULT.getPatternClass();
        final Object pattern = OlxDefaultPattern.builder()
                .descriptionPatterns(new String[]{"test1, test2, test3"})
                .priceType(PriceType.ALL)
                .build();
        final Object value = new String[]{"new1", "new2", "new3"};
        final var field = pattern.getClass().getDeclaredField("descriptionPatterns");
        field.setAccessible(true);
        field.set(pattern, value);
        System.out.println(pattern);
    }
}
