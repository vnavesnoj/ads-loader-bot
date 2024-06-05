package vnavesnoj.ads_loader_bot_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "vnavesnoj.ads_loader_bot_common.database.entity")
public class AdsLoaderBotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsLoaderBotServiceApplication.class, args);
    }

}
