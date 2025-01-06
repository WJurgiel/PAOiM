package org.example.test.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String sayHello(@RequestParam(value="name") String name) {
        return "Hello " + name + "!";
    }
    @GetMapping("/")
    public String Welcome() {
        return "Pozdrawiam wszystkich";
    }
}
