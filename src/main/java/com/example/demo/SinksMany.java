package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class SinksMany {
    public static void main(String[] args) {
        //Sinks.many { unitCast(), multiCast() }
        Sinks.Many<Integer> many = Sinks.many().replay().limit(2);
        Flux<Integer> flux = many.asFlux();

        many.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        many.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        many.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        flux.subscribe(data -> Logger.onNext("Subscriber1 ", data));
        flux.subscribe(data -> Logger.onNext("Subscriber2 ", data));
    }
}
