package com.example.demo;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class SchedulerNewBoundedElasticExample {
    public static void main(String[] args) {
        Scheduler scheduler = Schedulers.newBoundedElastic(2, 2, "I/O-Thread");
        Mono<Integer> mono =
                Mono
                        .just(1)
                        .subscribeOn(scheduler);

        Logger.info("# Start");

        mono.subscribe(data -> {
           Logger.onNext("subscribe 1 doing", data);
           TimeUtils.sleep(3000L);
           Logger.onNext("subscribe 1 done", data);
        });

        mono.subscribe(data -> {
           Logger.onNext("subscribe 2 doing", data);
           TimeUtils.sleep(3000L);
           Logger.onNext("subscribe 2 done", data);
        });

        mono.subscribe(data -> {
            Logger.onNext("subscribe 3 doing", data);
        });

        mono.subscribe(data -> {
            Logger.onNext("subscribe 4 doing", data);
        });

        mono.subscribe(data -> {
            Logger.onNext("subscribe 5 doing", data);
        });
    }
}
