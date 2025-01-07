package org.example.teachersspringboot.Controllers;

import org.example.teachersspringboot.Exceptions.TeacherSalaryException;
import org.example.teachersspringboot.InputService.TeacherInputService;
import org.example.teachersspringboot.Repositories.ClassRepository;
import org.example.teachersspringboot.Repositories.TeacherRepository;
import org.example.teachersspringboot.Teacher;
import org.example.teachersspringboot.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;

    public TeacherController(TeacherRepository teacherRepository, ClassRepository classRepository) {
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
    }
    @GetMapping
    public ResponseEntity<byte[]> getAllTeachersInCSV() throws IOException {
        List<Teacher> teachers = teacherRepository.findAll();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(byteArrayOutputStream);

        writer.write("Name,Surname,Salary,Year of Birth\n");

        for (Teacher teacher : teachers) {
            writer.write(String.format("%s,%s,%s,%d\n",
                    teacher.getName(),
                    teacher.getSurname(),
                    teacher.getSalary(),
                    teacher.getBirthYear()));
        }

        writer.flush();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/csv");
        headers.add("Content-Disposition", "attachment; filename=teachers.csv");

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addTeacher(@RequestBody TeacherDto teacherDto) {
        System.out.println("POST /teachers hit");
        System.out.println("Received birthYear: " + teacherDto.getBirthYear());
        try{
            var group = classRepository.findById(teacherDto.getGroupID())
                   .orElseThrow(() -> new IllegalArgumentException("Group does not exist"));
            Teacher t = new Teacher();
            t.setName(teacherDto.getName());
            t.setSurname(teacherDto.getSurname());
            t.setStatus(teacherDto.getStatus());
            t.setSalary(teacherDto.getSalary());
            t.setBirthYear(teacherDto.getBirthYear());
            t.setGroupID(group);

            teacherRepository.save(t);
            return new ResponseEntity<>("Teacher added successfully", HttpStatus.CREATED);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(DataIntegrityViolationException e){
            if(e.getCause() instanceof SQLException){
                SQLException sqlException = (SQLException) e.getCause();
                if("45000".equals(sqlException.getSQLState())){
                    return new ResponseEntity<>("Wynagrodzenie musi wynosić co najmniej 3000", HttpStatus.CONFLICT);
                }
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        System.out.println(id);
        try{
            var teacher = teacherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher does not exist"));
            teacherRepository.delete(teacher);
            return new ResponseEntity<>("Teacher deleted successfully", HttpStatus.OK);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
