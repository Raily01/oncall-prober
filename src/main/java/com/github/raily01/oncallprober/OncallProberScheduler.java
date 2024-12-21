package com.github.raily01.oncallprober;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OncallProberScheduler {

    private final MetricsManager metricsManager;

    private final OncallClient oncallClient;

    @Scheduled(fixedDelayString = "${app.scrapeInterval}")
    public void probe() {
        log.info("executing probe");
        long startTime = System.currentTimeMillis();
        try {
            if (HttpStatus.SC_OK == oncallClient.listUsers()) {
                metricsManager.success((int)((System.currentTimeMillis() - startTime) / 1000.0));
                log.info("probe succeed");
            } else {
                metricsManager.fail((int)((System.currentTimeMillis() - startTime) / 1000.0));
                log.info("probe failed");
            }
        } catch (Exception e) {
            metricsManager.fail((int)((System.currentTimeMillis() - startTime) / 1000.0));
            log.info("probe failed");
        }
    }
}
