package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Class;
import org.example.teachersspringboot.Repositories.ClassRepository;
import org.example.teachersspringboot.dto.GroupDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class ClassController {
    private final ClassRepository classRepository;

    public ClassController(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @GetMapping
    public List<Class> getAllGroups(){
        return classRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto){
        try{
            Class group = new Class();
            group.setName(groupDto.getName());
            group.setMaxTeachers(groupDto.getMaxTeachers());

            classRepository.save(group);
            return new ResponseEntity<>("Group created", HttpStatus.CREATED);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
