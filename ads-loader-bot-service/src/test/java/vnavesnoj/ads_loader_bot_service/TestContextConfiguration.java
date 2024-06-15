package vnavesnoj.ads_loader_bot_service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@SpringBootApplication
@EntityScan("vnavesnoj/ads_loader_bot_common/database/entity")
public class TestContextConfiguration {
}
