package vnavesnoj.ads_loader_bot_service.integration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import vnavesnoj.ads_loader_bot_service.annotation.IT;
import vnavesnoj.ads_loader_bot_service.config.TestContainersConfig;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Import(TestContainersConfig.class)
@IT
public class FilterBuilderServiceTest {
}
