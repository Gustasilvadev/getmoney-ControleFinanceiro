package com.getmoney.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class WebController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> home() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/index.html");
        InputStream inputStream = resource.getInputStream();
        String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return ResponseEntity.ok(body);
    }

    @GetMapping(value = "/install", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> install() throws Exception {
        ClassPathResource resource = new ClassPathResource("static/install.html");
        InputStream inputStream = resource.getInputStream();
        String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        return ResponseEntity.ok(body);
    }
}