package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Exceptions.TeacherSalaryException;
import org.example.teachersspringboot.Rate;
import org.example.teachersspringboot.Repositories.ClassRepository;
import org.example.teachersspringboot.Repositories.RateRepository;
import org.example.teachersspringboot.Teacher;
import org.example.teachersspringboot.dto.RateDto;
import org.example.teachersspringboot.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {
    private final RateRepository rateRepository;
    private final ClassRepository classRepository;

    public RateController(RateRepository rateRepository, ClassRepository classRepository) {
        this.rateRepository = rateRepository;
        this.classRepository = classRepository;
    }
    @GetMapping
    public List<Rate> getAllTeachers(){
        return rateRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<?> addTeacher(@RequestBody RateDto rateDto) {
        try {
            var group = classRepository.findById(rateDto.getGroupID())
                    .orElseThrow(() -> new IllegalArgumentException("Group does not exist"));
            Rate rate = new Rate();
            rate.setValue(rateDto.getValue());
            rate.setGroupID(group);
            rate.setComment(rateDto.getComment());
            rateRepository.save(rate);
            return new ResponseEntity<>("Rate added successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}