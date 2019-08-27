package com.example.demo.controller;

import org.slf4j.MDC;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.requireNonNull;

@RestController
@EnableConfigurationProperties(Configuration.class)
public class GreetingController {

    private final RestTemplate restTemplate;

    public GreetingController(RestTemplateBuilder restTemplateBuilder, Configuration configuration) {
        this.restTemplate = restTemplateBuilder.rootUri(configuration.getDemoUrl()).build();
    }

    @RequestMapping("/greeting")
    public Greeting greeting() {
        requireNonNull(MDC.get("user-id"));
        Greeting forObject = restTemplate.getForObject("/greeting", Greeting.class);
        requireNonNull(MDC.get("user-id"));
        return forObject;
    }
}