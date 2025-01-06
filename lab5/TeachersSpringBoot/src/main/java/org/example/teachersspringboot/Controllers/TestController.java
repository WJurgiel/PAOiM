package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @GetMapping("/")
    public String GreetUser(){
        return "Witaj";
    }
}
