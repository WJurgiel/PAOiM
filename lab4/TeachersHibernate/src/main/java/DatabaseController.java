import entity.Class;
import entity.Teacher;
import jakarta.persistence.*;

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
    public void ReturnTeachersForGroup(int groupID){
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
    public void FindTeacher(String surnameFrag){
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
    public void FindTeacherBornAfter(int year){
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
    public void FindTeacherWithSalaryLowerThan(int salary){
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
    public void Close(){
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
