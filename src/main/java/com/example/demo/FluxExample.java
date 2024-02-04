package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
public class FluxExample {
    public static void main(String[] args) {
        Flux<Object> flux =
                Mono.justOrEmpty(Optional.empty())
                        .concatWith(Mono.justOrEmpty("Jobs"));

        flux.subscribe(data -> log.info("# result = {} ", data));

        Flux.concat(
                Flux.just("Venus"),
                Flux.just("Earth"),
                Flux.just("Mars")
                ).collectList()
                .subscribe(list -> log.info("List: {} ", list));
    }
}
