package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BackPressureStrategyLatestExample {
    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureLatest()
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    TimeUtils.sleep(5L);
                    Logger.onNext(data);
                }, Logger::onError);

        TimeUtils.sleep(2000L);
    }
}
