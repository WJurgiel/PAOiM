import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.print("Unsorted\n");
        Teacher teacher1 = new Teacher("Wojciech", "Jurkiewicz", TeacherConditions.ABSENT, 1987, 4000);
        ClassTeacher class1 = new ClassTeacher("Klasa 1", 5);
        ClassTeacher class2 = new ClassTeacher("Klasa 2", 10);
        ClassContainer container1 = new ClassContainer();

        container1.addClass("Klasa 1");

        class1.addTeacher(teacher1);
        class1.addTeacher(new Teacher("Jojciech", "Jurkiewicz", TeacherConditions.SICK, 1986, 4500));
        class1.addTeacher(new Teacher("Mariusz", "Choma", TeacherConditions.DELEGATION, 1986, 4550));
        class1.addTeacher(new Teacher("Joanna", "Tysielec", TeacherConditions.PRESENT, 1988, 3999));
        class1.addTeacher(new Teacher("Jakub", "Samolot", TeacherConditions.PRESENT, 1995, 4100));

//        class1.summary();

//        class1.sortByName();
//        System.out.print("Sorted by salary\n");
        class1.sortBySalary();
        class1.summary();
//        class1.summary();
//
//        class1.removeTeacher(class1.teachers.get(0));
//        class1.removeTeacher(class1.teachers.get(0));
//        class1.removeTeacher(class1.teachers.get(0));

        System.out.printf("\n\n") ;
        class1.summary();

//        int counter = class1.countByCondition(TeacherConditions.PRESENT);
//        System.out.println(TeacherConditions.PRESENT.toString() + ": " + counter) ;

        System.out.printf("\nSearch:\n");


        class1.search("Jurkiewicz");

        System.out.printf("\nSearch partial:\n");
        class1.searchPartial("mar");

        System.out.printf("\nMax:\n");
       class1.max();


        System.out.println(class1.teachers.get(2).compareTo(teacher1));
    }
}