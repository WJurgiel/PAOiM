import java.util.Comparator;

public class SalaryComparator implements Comparator<Teacher> {
    public int compare(Teacher t1, Teacher t2) {
//        int salaryDiff = (int) (t1.getSalary() - t2.getSalary());
//        if(salaryDiff == 0 ){
//            return t1.compareTo(t2);
//        }
//        return salaryDiff;
        Integer salary1 = Integer.valueOf((int)t1.getSalary());
        Integer salary2 = Integer.valueOf((int)t2.getSalary());

        return Integer.compare(salary1, salary2);
    }

}
