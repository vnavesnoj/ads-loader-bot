package vnavesnoj.ads_loader_bot_configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "vnavesnoj.ads_loader_bot_service",
        "vnavesnoj.ads_loader_bot_web"
})
public class AdsLoaderBotConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsLoaderBotConfigurationApplication.class, args);
    }

}
