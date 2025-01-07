package org.example.teachersspringboot.dto;

public class TeacherDto {
    private String name;
    private String surname;
    private String status;
    private Integer salary;
    private Integer birthYear;
    private Integer groupID;

    public TeacherDto(String name, String surname, String status, Integer salary, Integer birthYear, Integer groupID) {
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.salary = salary;
        this.birthYear = birthYear;
        this.groupID = groupID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
