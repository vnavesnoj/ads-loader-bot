package vnavesnoj.ads_loader_bot_web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Component
public class WordPatternMapper implements StringValueMapper<String[]> {

    private static final String OLX_DEFAULT_PATTERN_DELIMITER = ",";

    @Override
    public String[] map(String patterns) {
        return Optional.of(patterns)
                .map(item -> item.split(OLX_DEFAULT_PATTERN_DELIMITER))
                .map(item -> Stream.of(item)
                        .map(String::strip)
                        .toArray(String[]::new))
                .orElseThrow();
    }
}
