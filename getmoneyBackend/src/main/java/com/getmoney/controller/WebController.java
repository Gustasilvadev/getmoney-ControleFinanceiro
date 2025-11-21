package com.getmoney.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/download-apk")
    public void downloadApk(HttpServletResponse response) throws IOException {
        String expoUrl = "https://expo.dev/artifacts/eas/application-0046c96b-7e06-4b35-93fa-ff5f42f50a8c.apk";

        response.setContentType("application/vnd.android.package-archive");
        response.setHeader("Content-Disposition", "attachment; filename=\"GetMoney.apk\"");

        URL url = new URL(expoUrl);
        try (InputStream in = url.openStream();
             OutputStream out = response.getOutputStream()) {
            in.transferTo(out);
        }
    }
}