package com.example.Banking_App.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

//    @GetMapping("/api/test")
//    public  String test() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = (String) auth.getPrincipal();
//
//        return "Hello " + username;
//    }

    @GetMapping("/admin/test")
    public String admin() {
        return "Hello Admin";
    }

    @GetMapping("/user/test")
    public String user() {
        return "Hello User";
    }
}
