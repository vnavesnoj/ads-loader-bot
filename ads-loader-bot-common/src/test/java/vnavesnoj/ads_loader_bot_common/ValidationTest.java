package vnavesnoj.ads_loader_bot_common;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
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
                .descriptionPatterns(new String[]{"123"})
                .build();
        final var dataBinder = new DataBinder(pattern);
        dataBinder.setValidator(validator);
        dataBinder.setAllowedFields(OlxDefaultPattern.Fields.descriptionPatterns);
        dataBinder.validate();
        final var bindingResult = dataBinder.getBindingResult();
        System.out.println();
    }
}
