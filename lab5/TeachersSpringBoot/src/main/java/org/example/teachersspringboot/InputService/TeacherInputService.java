package org.example.teachersspringboot.InputService;

import org.example.teachersspringboot.Class;
import org.example.teachersspringboot.Repositories.ClassRepository;
import org.example.teachersspringboot.Repositories.TeacherRepository;
import org.example.teachersspringboot.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Service
public class TeacherInputService {
    @Autowired
    private ClassRepository classRepository;
    private static final String ADD_TEACHER_URL = "http://localhost:8080/add-teacher";
    private final TeacherRepository teacherRepository;
    private final Scanner scanner;

    public TeacherInputService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.scanner = new Scanner(System.in);
    }
    public void addTeacherFromInput() {
        System.out.println("Podaj imię nauczyciela:");
        String name = scanner.nextLine();

        System.out.println("Podaj nazwisko nauczyciela:");
        String surname = scanner.nextLine();

        System.out.println("Podaj rok urodzenia nauczyciela:");
        int birthYear = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Podaj pensję nauczyciela:");
        int salary = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Podaj status nauczyciela (np. Active):");
        String status = scanner.nextLine();

        System.out.println("Podaj ID grupy:");
        int groupId = scanner.nextInt();
        scanner.nextLine();
        Class group = classRepository.findById(groupId).orElse(null);
        if (group == null) {
            System.out.println("Grupa o podanym ID nie istnieje!");
            return;
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setBirthYear(birthYear);
        teacher.setSalary(salary);
        teacher.setStatus(status);
        teacher.setGroupID(group);


        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(ADD_TEACHER_URL, teacher, String.class);
            System.out.println("Nauczyciel został dodany!");
        } catch (HttpClientErrorException e) {
            System.out.println("Błąd podczas dodawania nauczyciela: " + e.getMessage());
        }
    }
}
