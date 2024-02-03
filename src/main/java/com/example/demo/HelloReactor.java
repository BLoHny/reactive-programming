package com.example.demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class HelloReactor {
    public static void main(String[] args) {
        Mono.just("Hello, Reactor")
                .subscribe(System.out::println);

        Flux<String> flux = Flux.just("Hello", "Reactor");
        flux
                .map(String::toLowerCase)
                .subscribe(System.out::println);
    }
}
