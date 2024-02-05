package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class SequenceType {
    public static void main(String[] args) throws InterruptedException {
        /* cold */
        Flux<String> cold = Flux.fromIterable(Arrays.asList("Red", "YELLOW", "PINK"))
                .map(String::toLowerCase);

        cold.subscribe(color -> log.info("# Subscribe 1 {} ", color));
        log.info("---------------------------------------");
        cold.subscribe(color -> log.info("# Subscribe 2 {} ", color));

        /* hot */
        Flux<String> hot =
                Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                        .delayElements(Duration.ofSeconds(1)).share();

        hot.subscribe(singer -> log.info("1 subscribe is watching {}'s song", singer));

        Thread.sleep(2500);

        hot.subscribe(singer -> log.info("2 subscribe is watching {}'s song", singer));

        Thread.sleep(3000);
    }
}
