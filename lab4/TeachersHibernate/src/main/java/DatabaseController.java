import entity.Class;
import entity.Rate;
import entity.Teacher;
import jakarta.persistence.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DatabaseController {
    private static DatabaseController instance;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private Scanner scanner;
    private DatabaseController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        scanner = new Scanner(System.in);
    }
    public static DatabaseController Instance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }
    public void CountTeachers(){
        try{
            transaction.begin();
            Query countTeachers = entityManager.createNativeQuery("SELECT COUNT(*) FROM teachers WHERE 1");
            System.out.println("Teachers count: " + countTeachers.getSingleResult());

            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void ReturnAllGroups(){
        try{
            transaction.begin();
            System.out.println("Displaying all groups: ");
            Query getGroups = entityManager.createNativeQuery("SELECT * FROM classes", Class.class);
            for(Class group : (List<Class>) getGroups.getResultList()){
                System.out.print(group);

                Query teachersInGroups = entityManager.createNativeQuery("SELECT COUNT(*) FROM teachers WHERE teachers.groupID = :idParam");
                teachersInGroups.setParameter("idParam", group.getId());

                Query avgRating = entityManager.createNativeQuery("SELECT AVG(value) from rate WHERE groupID = :idParam");
                avgRating.setParameter("idParam", group.getId());

                System.out.print(teachersInGroups.getSingleResult() + "/" + group.getMaxTeachers().toString() + " | ");
                System.out.println("Average rating: " + avgRating.getSingleResult());
            }

            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void ReturnAllTeachers(){
        try{
            transaction.begin();
            Query getTeachers = entityManager.createNativeQuery("SELECT t.*, c.name FROM teachers t JOIN classes c ON t.groupID = c.ID", Teacher.class);
            for(Teacher teacher : (List<Teacher>) getTeachers.getResultList()){
                System.out.println(teacher);
            }

            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void ReturnTeachersForGroup(){
        System.out.print("group ID: ");
        int groupID = scanner.nextInt();
        scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("Displaying teachers for group with ID: " + groupID);
            Query getTeachersInGroup = entityManager.createNativeQuery("SELECT * FROM teachers t WHERE t.groupID = :GroupIDParam", Teacher.class);
            getTeachersInGroup.setParameter("GroupIDParam", groupID);
            for(Teacher teacher : (List<Teacher>) getTeachersInGroup.getResultList()){
                System.out.println(teacher);
            }

            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void SortTeachersBySalary(){
        try{
            System.out.println("Displaying teachers by salary (descending)");
            transaction.begin();
            Query getTeachersBySalary = entityManager.createNativeQuery("SELECT * FROM teachers ORDER BY Salary DESC", Teacher.class);
            for(Teacher teacher : (List<Teacher>) getTeachersBySalary.getResultList()){
                System.out.println(teacher);
            }
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void FindTeacher(){
        System.out.print("Fragment: ");
        String surnameFrag = scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("Displaying teachers with surname pattern: " + surnameFrag);
            String searchParam = "%" +surnameFrag.toLowerCase() + "%";
            Query findTeacherWithPatter = entityManager.createNativeQuery("SELECT * FROM teachers WHERE LOWER(Surname) LIKE :surParam OR LOWER(Name) LIKE :surParam", Teacher.class);
            findTeacherWithPatter.setParameter("surParam", searchParam);

            for(Teacher teacher : (List<Teacher>) findTeacherWithPatter.getResultList()){
                System.out.println(teacher);
            }
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void FindTeacherBornAfter(){
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("Displaying teachers born after: " + year);
            Query findTeacherWithPatter = entityManager.createNativeQuery("SELECT * FROM teachers WHERE BirthYear > :birthYear",Teacher.class);
            findTeacherWithPatter.setParameter("birthYear", year);

            for(Teacher teacher : (List<Teacher>) findTeacherWithPatter.getResultList()){
                System.out.println(teacher);
            }
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void FindTeacherWithSalaryLowerThan(){
        System.out.print("Salary: ");
        int salary = scanner.nextInt();
        scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("Displaying teachers that salary is lower than: " + salary);
            Query findTeacherWithPatter = entityManager.createNativeQuery("SELECT * FROM teachers WHERE teachers.Salary < :teacherSalary",Teacher.class);
            findTeacherWithPatter.setParameter("teacherSalary", salary);

            for(Teacher teacher : (List<Teacher>) findTeacherWithPatter.getResultList()){
                System.out.println(teacher);
            }
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void AddTeacher(){
        System.out.print("New teacher name: ");
        String name = scanner.nextLine();
        System.out.print("New teacher surname: ");
        String surname = scanner.nextLine();
        System.out.format("New teacher status: 0 - Present, 1 - Delegation, 2 - Sick, 3 - Absent");
        int iStatus  = scanner.nextInt();
        scanner.nextLine();
        String status = (iStatus == 0) ? "Present" : (iStatus == 1) ? "Delegation" : (iStatus == 2) ? "Sick" : "Absent";
        System.out.print("New teacher salary (Remember that teacher has to earn more than minimum wage): ");
        int salary = scanner.nextInt(); // it's not checking the salary in code, because i want to present the trigger effect in database
        System.out.print("New teacher birthYear (Remember that teacher has to be older than 18): ");
        int birthYear = scanner.nextInt(); // same thing here
        System.out.print("Assign teacher to class (give proper ID): ");
        int classID = scanner.nextInt();

        try{
            transaction.begin();
            System.out.println("New teacher added: ");
            Query addTeacher = entityManager.createNativeQuery(
                    "INSERT INTO teachers (Name, Surname, Status, Salary, BirthYear, groupID) VALUES (:name, :surname, :status, :salary, :birthYear, :groupID)");

            addTeacher.setParameter("name", name);
            addTeacher.setParameter("surname", surname);
            addTeacher.setParameter("status", status);
            addTeacher.setParameter("salary", salary);
            addTeacher.setParameter("birthYear", birthYear);
            addTeacher.setParameter("groupID", classID);

            addTeacher.executeUpdate();
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void AddGroup(){
        System.out.println("New group name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()) {
            System.out.println("The group has to have a name");
            return;
        }
        System.out.print("Max size:");
        int maxSize = scanner.nextInt();
        if(maxSize <= 0) {
            System.out.println("Max size cannot be negative or zero");
            return;
        }
        scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("New group added: ");
            Query addGroup = entityManager.createNativeQuery(
                    "INSERT INTO classes (Name, max_teachers) VALUES (:name, :maxSize)");
            addGroup.setParameter("name", name);
            addGroup.setParameter("maxSize", maxSize);
            addGroup.executeUpdate();
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }


    }
    public void GiveRating(){
        System.out.print("Giving a rate to group: ");
        int groupID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Rate [0-6]: ");
        int rating = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Your comment: ");
        String comment = scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("New rate added for group with ID:  " + groupID);
            Query addRate = entityManager.createNativeQuery(
                    "INSERT INTO rate (value, groupid, comment) VALUES (:value, :groupID, :comment)");
            addRate.setParameter("value", rating);
            addRate.setParameter("groupID", groupID);
            addRate.setParameter("comment", comment);

            addRate.executeUpdate();
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void AddToSalary(){
        System.out.print("Add salary to teacher with ID: ");
        int teacherID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Salary amount to add  (yes, you can also subtract the salary): ");
        int addition = scanner.nextInt();
        scanner.nextLine();
        try{
            transaction.begin();
            System.out.println("Added " + addition + " to teacher with ID: " + teacherID);
            Query addSalary = entityManager.createNativeQuery(
                    "UPDATE teachers SET Salary = Salary + :addition WHERE ID = :teacherID");
            addSalary.setParameter("addition", addition);
            addSalary.setParameter("teacherID", teacherID);
            addSalary.executeUpdate();
            transaction.commit();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
    public void ExportDatabaseToCSV(){
        // Wykonanie zapytania i pobranie listy nauczycieli
        List<Teacher> teachers = getEntities(Teacher.class);
        List<Class> classes = getEntities(Class.class);
        List<Rate> rates = getEntities(Rate.class);

        // Zapis do pliku CSV
        try (FileWriter writer = new FileWriter("database_export.csv")) {
            writer.write("ID;Name;Surname;Status;Salary;BirthYear;GroupID\n");

            // Zapis każdego nauczyciela do pliku
            for (Teacher teacher : teachers) {
                writer.write(String.format("%d;%s;%s;%s;%d;%d;%s\n",
                        teacher.getId(),
                        teacher.getName(),
                        teacher.getSurname(),
                        teacher.getStatus(),
                        teacher.getSalary(),
                        teacher.getBirthYear(),
                        teacher.getGroupID()));
            }

            writer.write("\n");
            writer.write("Groups:\n");
            writer.write("ID;class name;max teachers\n");
            for(Class c : classes){
                writer.write(String.format("%d;%s;%d\n",
                        c.getId(), c.getName(), c.getMaxTeachers()));
            }

            writer.write("\n");
            writer.write("Rates:\n");
            writer.write("ID;value;group ID; comment\n");
            for(Rate r : rates){
                writer.write(String.format("%d;%d; %s;%s\n",
                        r.getId(),r.getValue(), r.getGroupID(), r.getComment()));
            }
            System.out.println("Databases exported to database_export.csv");
        }catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }
    public <T> List<T> getEntities(java.lang.Class<T> entityClass) {
        Query query;
        if (entityClass.equals(Teacher.class)) {
            query = entityManager.createNativeQuery("SELECT * FROM teachers", entityClass);
        } else if (entityClass.equals(Class.class)) {
            query = entityManager.createNativeQuery("SELECT * FROM classes", entityClass);
        }else{
            query = entityManager.createNativeQuery("SELECT * FROM rate", entityClass);
        }
        return query.getResultList();
    }
    public void Close(){
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
