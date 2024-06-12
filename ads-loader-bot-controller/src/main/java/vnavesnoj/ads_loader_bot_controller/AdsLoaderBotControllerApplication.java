package vnavesnoj.ads_loader_bot_controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "vnavesnoj.ads_loader_bot_service",
        "vnavesnoj.ads_loader_bot_controller"})
public class AdsLoaderBotControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdsLoaderBotControllerApplication.class, args);
    }

}
