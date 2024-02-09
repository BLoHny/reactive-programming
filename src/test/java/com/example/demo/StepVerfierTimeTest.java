package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;

public class StepVerfierTimeTest {

    public static Flux<Tuple2<String, Integer>> count(Flux<Long> source) {
        return source
                .flatMap(notUse -> Flux.just(
                        Tuples.of("서울", 1000),
                        Tuples.of("경기도", 500),
                        Tuples.of("강원도", 300),
                        Tuples.of("충청도", 60),
                        Tuples.of("경상도", 100),
                        Tuples.of("전라도", 80),
                        Tuples.of("인천", 200),
                        Tuples.of("대전", 50),
                        Tuples.of("대구", 60),
                        Tuples.of("부산", 30),
                        Tuples.of("제주도", 5)
                ));
    }

    @Test
    public void test() {
        StepVerifier
                .withVirtualTime(() -> count(Flux.interval(Duration.ofHours(12)).take(1)))
                .expectSubscription()
                .then(() -> VirtualTimeScheduler.get().advanceTimeBy(Duration.ofHours(12)))
                .expectNextCount(11)
                .expectComplete()
                .verify();
    }

    @Test
    public void test_2() {
        StepVerifier
                .withVirtualTime(() -> count(Flux.interval(Duration.ofHours(12)).take(1)))
                .expectSubscription()
                .thenAwait(Duration.ofHours(12))
                .expectNextCount(11)
                .expectComplete()
                .verify();
    }
}
