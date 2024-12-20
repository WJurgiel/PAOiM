import entity.Teacher;
import jakarta.persistence.*;

import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int action;
        Scanner sc = new Scanner(System.in);
        DatabaseController db = DatabaseController.Instance();
        do{
            System.out.printf("\n==================================\n[1]: Return all teachers, [2]: Return all groups, [3]: Return teachers for group\n" +
                    "[4]: Count all teachers, [5]: Sort teachers by salary, [6]: Find teacher\n" +
                    "[7]: Find teacher born after, [8]: Find teacher with salary lower than\n" +
                    "[9]: Add teacher, [10]: Add group, [11]: Add rating, [12]: Add to salary\n" +
                    "[13]: export database to csv\n==================================\n");
            System.out.println("Enter action: ");
            action = sc.nextInt();
            sc.nextLine();
            switch(action){
                case 1: db.ReturnAllTeachers();break;
                case 2: db.ReturnAllGroups();break;
                case 3: db.ReturnTeachersForGroup();break;
                case 4: db.CountTeachers();break;
                case 5: db.SortTeachersBySalary();break;
                case 6: db.FindTeacher();break;
                case 7: db.FindTeacherBornAfter();break;
                case 8: db.FindTeacherWithSalaryLowerThan();break;
                case 9: db.AddTeacher();break;
                case 10: db.AddGroup();break;
                case 11: db.GiveRating();break;
                case 12: db.AddToSalary();break;
                case 13: db.ExportDatabaseToCSV(); break;
                default: db.Close(); return;
            }
        }while(true);



    }
}
