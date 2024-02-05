package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BackPressureStrategyErrorExample {
    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(Logger::doOnNext)
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    TimeUtils.sleep(5L);
                    Logger.onNext(data);
                }, Logger::onError);

        TimeUtils.sleep(2000L);
    }
}
