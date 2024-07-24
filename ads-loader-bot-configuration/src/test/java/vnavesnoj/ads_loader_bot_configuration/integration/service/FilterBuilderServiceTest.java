package vnavesnoj.ads_loader_bot_configuration.integration.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@SpringBootTest
public class FilterBuilderServiceTest {

    private final FilterBuilderService filterBuilderService;

    @Test
    void testValidation() {
        Assertions.assertThatExceptionOfType(UnknownInputFieldException.class)
                .isThrownBy(() -> filterBuilderService.updateCurrentInput(18L, "dummy"));
        Assertions.assertThatNoException()
                .isThrownBy(() -> filterBuilderService.updateCurrentInput(18L, "descriptionPatterns"));
    }
}
