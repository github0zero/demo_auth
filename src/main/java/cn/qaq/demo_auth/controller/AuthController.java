package cn.qaq.demo_auth.controller;

import cn.qaq.dubbo.service.EnhanceUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/auth")
@Slf4j
@RefreshScope
public class AuthController {

    @Autowired
    DataSource dataSource;

    @Autowired
    EnhanceUserDetailsService enhanceUserDetailsService;

    @Value("${demo_auth.config}")
    private String st;

    @GetMapping("/test")
    public String test(){
        return "auth test: "+st;
    }

    @GetMapping("/user")
    public String user(){
         enhanceUserDetailsService.newUser("auth");
         return "ok";
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
