import java.util.HashMap;
import java.util.Map;

public class ClassContainer {
    Map<String, ClassTeacher> container = new HashMap<>();

    void addClass(String name, int size){
        container.put(name, new ClassTeacher(name, size));
    }
    void removeClass(String name){
        container.remove(name);
    }
    void findEmpty(){
        for(Map.Entry<String, ClassTeacher> entry : container.entrySet()){
            ClassTeacher classTeacher = entry.getValue();

            if(classTeacher.teachers.size() == 0) System.out.println(entry.getKey());

        }
    }
    void summary(){
        for(Map.Entry<String, ClassTeacher> entry : container.entrySet()){
            ClassTeacher classTeacher = entry.getValue();
            System.out.println(entry.getKey() + ": " + classTeacher.teachers.size() / classTeacher.maxTeachers + "%");
        }
    }
}
