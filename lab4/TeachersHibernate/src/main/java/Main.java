import entity.Teacher;
import jakarta.persistence.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        DatabaseController db = DatabaseController.Instance();
//        db.CountTeachers();
//        db.ReturnAllTeachers();
//        db.ReturnTeachersForGroup(1);
//        db.ReturnAllGroups();
        db.SortTeachersBySalary();
        db.Close();
    }
}
