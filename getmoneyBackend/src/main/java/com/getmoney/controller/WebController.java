package com.getmoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String downloadApk() {
        return "redirect:https://expo.dev/artifacts/eas/9kSBQj3ZJaBTduZ2QPj9vz.apk";
    }
}