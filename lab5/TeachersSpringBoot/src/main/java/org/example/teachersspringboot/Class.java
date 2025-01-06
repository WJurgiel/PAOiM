package org.example.teachersspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "maxTeachers", nullable = false)
    private Integer maxTeachers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxTeachers() {
        return maxTeachers;
    }

    public void setMaxTeachers(Integer maxTeachers) {
        this.maxTeachers = maxTeachers;
    }

}