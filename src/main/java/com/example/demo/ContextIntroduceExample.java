package com.example.demo;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ContextIntroduceExample {
    public static void main(String[] args) {
        String key = "message";
        Mono<String> mono = Mono.deferContextual(ctx ->
                Mono.just("Hello" + " " + ctx.get(key)).doOnNext(Logger::doOnNext)
        )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual((mono2, ctx) -> mono2.map(data -> data + " " + ctx.get(key)))
                .contextWrite(context -> context.put(key, "Reactor"));

        mono.subscribe(Logger::onNext);

        TimeUtils.sleep(100L);
    }
}
