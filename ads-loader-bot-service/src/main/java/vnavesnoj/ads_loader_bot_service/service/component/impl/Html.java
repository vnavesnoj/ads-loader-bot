package vnavesnoj.ads_loader_bot_service.service.component.impl;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@ToString(exclude = "html")
public class Html {

    private final String href;

    private final String html;

    private final LocalDateTime connectionTime;
}
