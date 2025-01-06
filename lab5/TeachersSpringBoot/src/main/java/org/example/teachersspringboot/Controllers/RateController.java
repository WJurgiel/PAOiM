package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Rate;
import org.example.teachersspringboot.Repositories.RateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RateController {
    private final RateRepository rateRepository;

    public RateController(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }
    @GetMapping("/rates")
    public List<Rate> getAllTeachers(){
        return rateRepository.findAll();
    }
}