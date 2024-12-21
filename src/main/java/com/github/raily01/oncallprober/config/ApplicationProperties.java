package com.github.raily01.oncallprober.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    @NotNull
    private String oncallApiUrl;

    @NotNull
    private Duration scrapeInterval;

    @NotNull
    private String apiUser;

    @NotNull
    private String apiKey;

    @NotNull
    private String hmacAlgorithm;
}
