package com.example.teachers;
import java.util.Comparator;

public class SalaryComparator implements Comparator<Teacher> {
    public int compare(Teacher t1, Teacher t2) {
        Integer salary1 = Integer.valueOf((int)t1.getSalary());
        Integer salary2 = Integer.valueOf((int)t2.getSalary());

        return Integer.compare(salary2, salary1);
    }

}
