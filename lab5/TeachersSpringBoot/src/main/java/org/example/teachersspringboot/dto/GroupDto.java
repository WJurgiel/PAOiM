package org.example.teachersspringboot.dto;

public class GroupDto {
    private String name;
    private int maxTeachers;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMaxTeachers() {
        return maxTeachers;
    }
    public void setMaxTeachers(int maxTeachers) {
        this.maxTeachers = maxTeachers;
    }
}
