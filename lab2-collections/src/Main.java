import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //predefined teacher
        Teacher teacher1 = new Teacher("Gordon", "Ramsay", TeacherConditions.ABSENT, 1987, 4000);

        // creating two example classes
        ClassTeacher class1 = new ClassTeacher("Klasa 1", 5);
        ClassTeacher class2 = new ClassTeacher("Klasa 2", 10);

        ClassContainer container1 = new ClassContainer();
        container1.addClass("Klasa 1", class1);
        container1.addClass("Klasa 2", class2);

        container1.summary();

        //add to class1
        class1.addTeacher(teacher1);
        class1.addTeacher(teacher1);
        class1.addTeacher(new Teacher("Ewa", "Werner", TeacherConditions.PRESENT, 1976, 2000));
        class1.addTeacher(new Teacher("Bogdan", "Werner", TeacherConditions.PRESENT, 1995, 8800));
        class1.addTeacher(new Teacher("Mariusz", "Pudzianowski", TeacherConditions.PRESENT, 1988, 3999));
        class1.addTeacher(new Teacher("Jeremiasz", "Pachnidelko", TeacherConditions.DELEGATION, 1986, 4550));
        class1.addTeacher(new Teacher("Adam", "Małysz", TeacherConditions.PRESENT, 1995, 4100));

        // add to class2
        container1.getClassTeacher("Klasa 2").addTeacher(new Teacher("Spongebob", "Kanciastoporty", TeacherConditions.PRESENT, 2003, 1400));
        class2.addTeacher(new Teacher("Pan", "Krab", TeacherConditions.DELEGATION, 1990, 9000));

        container1.summary();

        System.out.println("2. ===============addSalary + Search=======================");
        class1.addSalary(teacher1, 10000);
        class1.search("Ramsay");

        System.out.println("3. ===============partialSearch(\"Wer\")=======================");
        class1.searchPartial("Wer");

        System.out.println("4. ===============Remove teacher=======================");
        class1.removeTeacher(teacher1);
        container1.summary();

        System.out.println("5. ===============Change condition=======================");
        class1.search("Pachnidelko");
        class1.changeCondition(class1.teachers.get(2), TeacherConditions.SICK);

        System.out.println("6. ===============count by condition=======================");
        class1.countByCondition(TeacherConditions.PRESENT);

        System.out.println("7. ===============summary=======================");
        class1.summary();

        System.out.println("8. ===============Sort by surname=======================");
        class1.sortByName();
        class1.summary();

        System.out.println("9. ===============sort by salary=======================");
        class1.sortBySalary();
        class2.sortBySalary();
        container1.summary();

        System.out.println("10. ===============max=======================");
        class1.max();
        class2.max();

        System.out.println("10. ===============delete class=======================");
        container1.removeClass("Klasa 2");
        container1.removeClass("Klasa 77");
        container1.summary();

        System.out.println("10. ===============find empty class=======================");
        container1.addClass("Klasa 3", 8);
        container1.findEmpty();
        container1.summary();

    }
}