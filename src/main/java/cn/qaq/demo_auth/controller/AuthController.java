package cn.qaq.demo_auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @GetMapping("/test")
    public String test(){
        return "auth test";
    }

    @GetMapping("/admin")
    public String admin(){
        return "auth admin";
    }

    @GetMapping("/resource")
    public String resource(){
        return "auth resource";
    }
}
