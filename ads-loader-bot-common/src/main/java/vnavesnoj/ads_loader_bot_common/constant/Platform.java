package vnavesnoj.ads_loader_bot_common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@RequiredArgsConstructor
@Getter
public enum Platform {

    OLXUA("olx.ua");

    private final String domain;
}
