package com.example.demo;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

@Slf4j
public class MonoExample {
    public static void main(String[] args) {
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(httpHeaders), String.class)
        )
                .map(response -> {
                    DocumentContext context = JsonPath.parse(response.getBody());
                    return context.<String>read("$.datetime");
                })
                .subscribe(
                        data -> log.info("# emitted data: " + data),
                        error -> {
                          log.error(String.valueOf(error));
                        },
                        () -> log.info("# emitted onComplete Signal")
                );
    }

}