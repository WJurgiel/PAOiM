import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    Map<String, ClassTeacher> container = new HashMap<>();

    void addClass(String name, int size){
        container.put(name, new ClassTeacher(name, size));
    }
    void addClass(String name, ClassTeacher classTeacher){
        container.put(name, classTeacher);
    }
    ClassTeacher getClassTeacher(String key){
        return container.get(key);
    }
    void removeClass(String key){
        if(container.containsKey(key)){
            System.out.println("Class: " + key + " removed");
            container.remove(key);
        }
        else System.out.println("Class " + key + " not found");

    }
    void findEmpty(){
        System.out.println("Empty classes:");
        for(Map.Entry<String, ClassTeacher> entry : container.entrySet()){
            ClassTeacher classTeacher = entry.getValue();

            if(classTeacher.teachers.isEmpty()) System.out.println(entry.getKey());

        }
    }
    void summary(){

        for(Map.Entry<String, ClassTeacher> entry : container.entrySet()){
            ClassTeacher classTeacher = entry.getValue();
            System.out.println("----------------------------");
            System.out.println("> " + entry.getKey() + ": " + Double.valueOf(classTeacher.teachers.size())/ classTeacher.maxTeachers * 100 + " %");
            if(classTeacher.teachers.isEmpty()) System.out.println("...");
            else classTeacher.summary();
        }
    }
}
