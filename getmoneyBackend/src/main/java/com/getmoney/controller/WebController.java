package com.getmoney.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URL;

@Controller
@CrossOrigin("*")
public class WebController {

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping("/install")
    public String install() {
        return "forward:/install.html";
    }

        @GetMapping("/download/app")
    public ResponseEntity<Resource> downloadApk() throws MalformedURLException {
        Path path = Paths.get("/app/apk/app-release.apk");
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"app-release.apk\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}