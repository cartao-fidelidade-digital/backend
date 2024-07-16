package com.clubeevantagens.authmicroservice.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class IndexController {

    private final ResourceLoader resourceLoader;

    public IndexController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Hidden
    @GetMapping("/")
    public ResponseEntity<String> index() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/index.html");
        String htmlContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
    }
}
