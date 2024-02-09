package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

public class PublisherProbeTest {
    @Test
    public void publisherProbeTest() {
        PublisherProbe<String> probe = PublisherProbe.of(useStandbyPower());

        StepVerifier
                .create(processWith(useMainPower(), probe.mono()))
                .expectNextCount(1)
                .verifyComplete();

        probe.assertWasSubscribed();
        probe.assertWasRequested();
        probe.assertWasNotCancelled();
    }

    private Mono<String> processWith(Mono<String> useMainPower, Mono<String> mono) {
        return useMainPower
                .flatMap(Mono::just)
                .switchIfEmpty(mono);
    }

    private Mono<String> useMainPower() {
        return Mono.empty();
    }

    private Mono<String> useStandbyPower() {
        return Mono
                .just("# use Standby Power");
    }
}
