import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassTeacher {
    String groupName;
    List<Teacher> teachers = new ArrayList<>();
    int maxTeachers;

    ClassTeacher(String groupName, int maxTeachers) {
        this.groupName = groupName;
        this.maxTeachers = maxTeachers;
    }

    void addTeacher(Teacher t) {
        if(teachers.size() < maxTeachers) teachers.add(t);
        else System.out.println("Couldn't add teacher: " + t.getFullName());
    }
    void addSalary(Teacher t, double salary){
        t.setSalary(salary);
    }
    void removeTeacher(Teacher t) {
        teachers.remove(t);
    }
    void changeCondition(Teacher t, TeacherConditions cond){
        t.setCondition(cond);
    }
    void search(String surname) {
        for(Teacher t : teachers) {
            if(t.getSurname().toLowerCase() == surname.toLowerCase()){
                t.printShort();
            }
        }
    }// use Comparator
    void searchPartial(String fragment){
        //fragment
        for(Teacher t: teachers){
            if(t.getFullName().toLowerCase().contains(fragment.toLowerCase())){
                t.printShort();
            }
        }
    }
    int countByCondition(TeacherConditions con){
        int counter = 0;
        for(Teacher t : teachers){
            if(t.getCondition() == con) counter++;
        }
        return counter;
    }
    void summary(){
        System.out.println(groupName);
        for(Teacher t : teachers){
            t.printShort();
        }
    }
    void sortByName(){
        System.out.println("Sorted by Name");
        Collections.sort(teachers);
    }
    void sortBySalary(){
        System.out.println("Sorted by salary");
       Collections.sort(teachers, new SalaryComparator());
    }
    void max(){
        Teacher maxSalaryTeacher = Collections.max(teachers, new SalaryComparator());
        maxSalaryTeacher.printShort();
    }
}
