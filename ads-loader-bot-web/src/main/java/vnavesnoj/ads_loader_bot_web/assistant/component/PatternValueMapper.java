package vnavesnoj.ads_loader_bot_web.assistant.component;

import vnavesnoj.ads_loader_bot_web.mapper.StringValueMapper;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface PatternValueMapper {

    void checkFields();

    Object map(String field, String value);

    StringValueMapper<?> getMapper(String field);
}
