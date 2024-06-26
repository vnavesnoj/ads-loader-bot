package vnavesnoj.ads_loader_bot_common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultAdBody;
import vnavesnoj.ads_loader_bot_common.pojo.OlxDefaultPattern;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Getter
public enum Platform {

    OLXUA("olx.ua", OlxDefaultPattern.class, OlxDefaultAdBody.class);

    private final String domain;

    private final Class<?> patternClass;

    private final Class<?> adBodyClass;
}
