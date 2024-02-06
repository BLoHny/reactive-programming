package com.example.demo;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class SinkOne {
    public static void main(String[] args) {
        Sinks.One<String> sinks = Sinks.one();
        Mono<String> mono = sinks.asMono();

        sinks.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> Logger.onNext("Subscriber1 ", data));
        mono.subscribe(data -> Logger.onNext("Subscriber2 ", data));
    }
}
