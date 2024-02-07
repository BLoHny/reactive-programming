package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulerExample {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> Logger.doOnNext("fromArray", data))
                //.publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> Logger.doOnNext("filter", data))
                //.publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> Logger.doOnNext("fromArray", data))
                .subscribe(Logger::onNext);

        TimeUtils.sleep(500L);
    }
}
