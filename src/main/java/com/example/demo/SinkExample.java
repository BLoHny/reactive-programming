package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.stream.IntStream;

@Slf4j
public class SinkExample {
    public static void main(String[] args) {
        int tasks = 6;

        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicastSink.asFlux();
        IntStream
                .range(1, tasks)
                .forEach(n -> {
                    new Thread(() -> {
                        unicastSink.tryEmitNext(doTask(n));
                        log.info("# emitted {}", n);
                    }).start();
                });

        fluxView.subscribe(data -> log.info("# onNext: {}", data));
    }

    private static String doTask(int n) {
        return "task " + n + " result";
    }
}
