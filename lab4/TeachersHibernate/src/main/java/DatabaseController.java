import entity.Class;
import entity.Teacher;
import jakarta.persistence.*;

import java.util.List;

public class DatabaseController {
    private static DatabaseController instance;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private DatabaseController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
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
//    public void ReturnTeacherSalaryGreater(int salary){}
    public void SortTeachersBySalary(){
        try{
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
    public void Close(){
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
