package org.example.teachersspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @Lob
    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Salary", nullable = false)
    private Integer salary;

    @Column(name = "BirthYear", nullable = false)
    private Integer birthYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupID")
    private Class groupID;

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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Class getGroupID() {
        return groupID;
    }

    public void setGroupID(Class groupID) {
        this.groupID = groupID;
    }

}