package com.github.raily01.oncallprober;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class OncallProberApplication implements CommandLineRunner {

    private final OncallClient oncallClient;

    public static void main(String[] args) {
        SpringApplication.run(OncallProberApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        oncallClient.listUsers();
    }
}