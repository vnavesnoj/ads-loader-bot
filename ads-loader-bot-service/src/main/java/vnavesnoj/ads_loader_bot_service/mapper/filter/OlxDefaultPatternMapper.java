package vnavesnoj.ads_loader_bot_service.mapper.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vnavesnoj.ads_loader_bot_persistence.database.entity.Filter;
import vnavesnoj.ads_loader_bot_service.mapper.Mapper;
import vnavesnoj.ads_loader_bot_service.service.component.impl.olxdefault.pojo.OlxDefaultPattern;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class OlxDefaultPatternMapper implements Mapper<Filter, OlxDefaultPattern> {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @SneakyThrows
    @Override
    public OlxDefaultPattern map(Filter object) {
        return objectMapper.readValue(object.getJsonPattern(), OlxDefaultPattern.class);
    }
}
