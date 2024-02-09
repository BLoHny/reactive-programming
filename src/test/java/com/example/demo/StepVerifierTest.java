package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StepVerifierTest {

    public static Flux<String> sayHello() {
        return Flux
                .just("Hello", "Reactor");
    }

    public static Flux<Integer> occurError(Flux<Integer> source) {
        return source
                .zipWith(Flux.just(2, 2, 2, 2, 0), (x, y) -> x / y);
    }

    @Test
    public void say() {
        StepVerifier
                .create(Mono.just("Hello Reactor"))
                .expectNext("Hello Reactor") //onNext Signal
                .expectComplete() //onComplete Signal
                .verify();
    }

    @Test
    public void say_2() {
        StepVerifier
                .create(sayHello())
                .expectSubscription()
                .expectNext("Hello")
                .expectNext("Reactor")
                .expectComplete()
                .verify();
    }

    @Test
    public void failed_say() {
        StepVerifier
                .create(sayHello())
                .expectSubscription()
                .as("# expect subscription")
                .expectNext("HI")
                .as("# expect HI")
                .expectNext("Reactor")
                .as("# expect Reactor")
                .verifyComplete();
    }

    @Test
    public void occurErrorTest() {
        Flux<Integer> flux = Flux.just(2, 4, 6, 8, 10);

        StepVerifier
                .create(occurError(flux))
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectError()
                .verify();
    }
}
