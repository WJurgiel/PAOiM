package com.example.teachers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClassTeacher {
    String groupName;
    ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    int maxTeachers;

    ClassTeacher(String groupName, int maxTeachers) {
        this.groupName = groupName;
        this.maxTeachers = maxTeachers;
    }
    public ObservableList<Teacher> getTeachers(){
        return teachers;
    }
    void addTeacher(Teacher t) {
        if(teachers.size() >= maxTeachers) {
            System.out.println("Couldn't add teacher: " + t.getFullName() + " | Reason: Group full");
            return;
        }
        if(teachers.contains(t)) {
            System.out.println("Couldn't add teacher: " + t.getFullName() + " | Reason: The teacher is aready in group");
            return;
        }
        teachers.add(t);
        System.out.println("Teacher added [ " + teachers.size() + " / " + maxTeachers + " ]");


    }
    void addSalary(Teacher t, double salary){
        t.setSalary(salary + t.getSalary());
    }
    void removeTeacher(Teacher t) {
        teachers.remove(t);
        System.out.println("Teacher removed [ " + t.getFullName() + " ]");
    }
    void changeCondition(Teacher t, TeacherConditions cond){
        TeacherConditions prevCond = t.getCondition();

        t.setCondition(cond);

        System.out.println(t.getFullName() + " " + prevCond + " -> " + cond);
    }
    public  void search(String surname) {
        Comparator<Teacher> surnameComparator = Comparator.comparing(Teacher::getSurname, String.CASE_INSENSITIVE_ORDER);

        teachers.stream()
                .filter(teacher -> {
                    return surnameComparator.compare(teacher, new Teacher("", surname, null, 0, 0)) == 0;
                })
                .forEach(System.out::println);

    }
    void searchPartial(String fragment){
        //fragment
        for(Teacher t: teachers){
            if(t.getFullName().toLowerCase().contains(fragment.toLowerCase())){
                t.printShort();
            }
        }
    }
    ObservableList<Teacher> searchTeacher(String fragment){
        ObservableList<Teacher> teachersToReturn = FXCollections.observableArrayList();
        for(Teacher t: teachers){
            if(t.getFullName().toLowerCase().contains(fragment.toLowerCase())){
                teachersToReturn.add(t);
            }
        }
        return teachersToReturn;
    }
    void countByCondition(TeacherConditions con){
        int counter = 0;
        for(Teacher t : teachers){
            if(t.getCondition() == con) counter++;
        }
        System.out.println(groupName + " - " + con + ": " + counter);
    }
    void summary(){
        for(Teacher t : teachers){
            System.out.println(t);
        }
    }
    void sortByName(){
        Collections.sort(teachers);
        System.out.println("Sorted by Name");
    }
    void sortBySalary(){
        System.out.println("Sorted by salary");
       Collections.sort(teachers, new SalaryComparator());
    }
    void max(){
        Teacher maxSalaryTeacher = Collections.max(teachers, (t1,t2) -> Integer.compare(
                Integer.valueOf((int)t1.getSalary()),
                Integer.valueOf((int) t2.getSalary()))
        );
        System.out.println(maxSalaryTeacher);
    }
    public String getTheClassName(){
        return groupName;
    }
    public String getFilling(){
        return String.valueOf(teachers.size()) + "/" + String.valueOf(maxTeachers);
    }
}
