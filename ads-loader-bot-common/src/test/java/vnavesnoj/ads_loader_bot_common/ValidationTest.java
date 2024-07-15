package vnavesnoj.ads_loader_bot_common;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import vnavesnoj.ads_loader_bot_common.annotation.IT;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@IT
public class ValidationTest {

    private final Validator validator;

    @Test
    void testValidation() {
        final var pattern = OlxDefaultPattern.builder()
                .descriptionPatterns(new String[]{"1234567890123456789"})
                .build();
        final var errors = validator.validateValue(
                OlxDefaultPattern.class,
                OlxDefaultPattern.Fields.descriptionPatterns,
                new String[]{"1234567890123456789"});
        errors.forEach(error -> System.out.println(error.getMessage()));
        System.out.println();
    }
}
