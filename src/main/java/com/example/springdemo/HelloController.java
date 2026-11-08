package com.example.springdemo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController
{
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello! Welcome to Spring Boot!";
    }
}

//    @RestController identifies the class as a Spring web controller
//        whose handler methods return data directly in the HTTP response body,
//        rather than rendering a traditional view