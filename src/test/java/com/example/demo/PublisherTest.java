package com.example.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class PublisherTest {

    @Test
    public void test() {
        TestPublisher<Integer> testPublisher = TestPublisher.create();

        StepVerifier
                .create(divideByTwo(testPublisher.flux()))
                .expectSubscription()
                .then(() -> testPublisher.next(2, 4, 6, 8, 10))
                .expectNext(1, 2, 3, 4, 5)
                .expectComplete()
                .verify();
    }

    @Test
    public void divideByTwoTest() {
        TestPublisher<Integer> source = TestPublisher.createNoncompliant(TestPublisher.Violation.ALLOW_NULL);
        StepVerifier
                .create(divideByTwo(source.flux()))
                .expectSubscription()
                .then(() -> source.next(2, 4, 6, 8, null))
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectComplete()
                .verify();
    }

    private Flux<Integer> divideByTwo(Flux<Integer> flux) {
        return flux
                .zipWith(Flux.just(2, 2, 2, 2, 2), (x, y) -> x / y);
    }
}
