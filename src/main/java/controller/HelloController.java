package com.example.demo.controller;

import com.example.demo.util.SecretsManagerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final SecretsManagerUtil secretsManagerUtil;

    // ✅ Constructor Injection
    public HelloController(SecretsManagerUtil secretsManagerUtil) {
        this.secretsManagerUtil = secretsManagerUtil;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/rds-password")
    public String getRdsPassword() {
        return secretsManagerUtil.getRDSPassword();  // ✅ Use injected bean
    }
}
