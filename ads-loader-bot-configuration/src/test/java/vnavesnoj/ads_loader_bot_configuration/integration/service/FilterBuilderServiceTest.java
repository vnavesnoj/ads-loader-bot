package vnavesnoj.ads_loader_bot_configuration.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import vnavesnoj.ads_loader_bot_common.constant.AnalyzerEnum;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;
import vnavesnoj.ads_loader_bot_service.dto.filterbuilder.FilterBuilderCreateDto;
import vnavesnoj.ads_loader_bot_service.exception.FilterBuilderAlreadyExistsException;
import vnavesnoj.ads_loader_bot_service.exception.SpotNotExistsException;
import vnavesnoj.ads_loader_bot_service.exception.UnknownInputFieldException;
import vnavesnoj.ads_loader_bot_service.service.FilterBuilderService;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@SpringBootTest
public class FilterBuilderServiceTest {

    private final FilterBuilderService filterBuilderService;

    @Test
    @Disabled
    void testValidation() {
        assertThatExceptionOfType(UnknownInputFieldException.class)
                .isThrownBy(() -> filterBuilderService.updateCurrentInput(18L, "dummy"));
        assertThatNoException()
                .isThrownBy(() -> filterBuilderService.updateCurrentInput(18L, "descriptionPatterns"));
    }

    @Test
    @Disabled
    void testCreateValidation() {
        assertThatExceptionOfType(SpotNotExistsException.class)
                .isThrownBy(() -> filterBuilderService.create(new FilterBuilderCreateDto(new Object(), 999, 999L)));
        assertThatExceptionOfType(FilterBuilderAlreadyExistsException.class).isThrownBy(() -> filterBuilderService.create(new FilterBuilderCreateDto(new Object(), 1, 389782356L)));
    }

    @Test
    void testPatternCast() {
        final var result = AnalyzerEnum.OLX_UA_DEFAULT.getPatternClass().isInstance(new OlxDefaultPattern());
        System.out.println(result);
    }
}
