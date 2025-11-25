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

    @GetMapping("/download-apk")
    @ResponseBody
    public void downloadApk(HttpServletResponse response) throws IOException {
        String expoUrl = "https://expo.dev/artifacts/eas/sNuVUNCNgZ3mSMFnYvE7X2.apk";

        response.setContentType("application/vnd.android.package-archive");
        response.setHeader("Content-Disposition", "attachment; filename=\"GetMoney.apk\"");

        URL url = new URL(expoUrl);
        try (InputStream in = url.openStream();
             OutputStream out = response.getOutputStream()) {
            in.transferTo(out);
        }
    }
}