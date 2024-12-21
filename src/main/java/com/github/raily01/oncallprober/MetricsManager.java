package com.github.raily01.oncallprober;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MetricsManager {

    private final MeterRegistry meterRegistry;

    private final Counter proberTotal;

    private final Counter proberSuccesTotal;

    private final Counter proberFailTotal;

    private final AtomicInteger proberDurationSeconds = new AtomicInteger(0);

    public MetricsManager(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.proberTotal = Counter.builder("prober.create.user.scenario.total")
                .description("Total count of runs to create user scenario to oncall API")
                .register(meterRegistry);
        this.proberSuccesTotal = Counter.builder("prober.create.user.scenario.success.total")
                .description("Total count of success runs the create user scenatio")
                .register(meterRegistry);
        this.proberFailTotal = Counter.builder("prober.create.user.scenario.fail.total")
                .description("Total count of success runs the create user scenatio")
                .register(meterRegistry);
        Gauge.builder("prober.create.user.scenario.duration.seconds", proberDurationSeconds::get)
                .description("Duration in seconds of runs the create user scenario")
                .register(meterRegistry);
    }

    public void success(Integer duration) {
        this.proberTotal.increment();
        this.proberSuccesTotal.increment();
        this.proberDurationSeconds.set(duration);
    }

    public void fail(Integer duration) {
        this.proberTotal.increment();
        this.proberFailTotal.increment();
        this.proberDurationSeconds.set(duration);
    }
}
