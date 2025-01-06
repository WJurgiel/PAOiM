package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Class;
import org.example.teachersspringboot.Repositories.ClassRepository;
import org.example.teachersspringboot.Repositories.TeacherRepository;
import org.example.teachersspringboot.Teacher;
import org.example.teachersspringboot.dto.GroupDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class ClassController {
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    public ClassController(ClassRepository classRepository, TeacherRepository teacherRepository) {
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Class> getAllGroups(){
        return classRepository.findAll();
    }
    @GetMapping("/{id}/teacher")
    public ResponseEntity<?>getTeachersFromGroup(@PathVariable Long id){
        try{
            List<Teacher> teachers = teacherRepository.findByGroupIDId(id);
            if (teachers.isEmpty()) {
                return ResponseEntity.status(404).body("No teachers found for the given group ID");
            }
            return ResponseEntity.ok(teachers);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/fill")
    public ResponseEntity<?>getFillOfGroup(@PathVariable int id){
        try{
            Class group = classRepository.findById(id).orElse(null);
            if(group == null){
                return ResponseEntity.status(404).body("No group found for the given group ID");
            }
            long teacherCount = teacherRepository.countByGroupIDId(id);
            int maxTeachers = group.getMaxTeachers();
            if(maxTeachers < 0){
                return ResponseEntity.badRequest().body("Max teachers is not set (or wrong)");
            }
            double fillPercentage = (teacherCount / (double) maxTeachers) * 100;
            return ResponseEntity.ok("Group's [" + id + "] fill percentage: " + fillPercentage + "%" );
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable int id) {
        try{
            var group = classRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher does not exist"));
            classRepository.delete(group);
            return new ResponseEntity<>("group deleted successfully", HttpStatus.OK);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
