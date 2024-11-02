public class Teacher implements Comparable<Teacher> {
    private String name;
    private String surname;
    private TeacherConditions teacherCondition;
    private Integer yearOfBirth;
    private double salary;

    public Teacher(String n, String sn, TeacherConditions tc, Integer year, Integer salar) {
        name = n;
        surname = sn;
        teacherCondition = tc;
        yearOfBirth = year;
        salary = salar;
    }
    public double getSalary(){
        return salary;
    }
    public void setSalary(double x){
        this.salary = x;
    }
    public TeacherConditions getCondition(){
        return teacherCondition;
    }
    public void setCondition(TeacherConditions tc){
        teacherCondition = tc;
    }
    public String getFullName(){
        return name + " " + surname;
    }
    public String getSurname(){
        return surname;
    }
    public void print(){
        System.out.println(name + " " + surname);
        System.out.println("Year of birth: " + yearOfBirth);
        System.out.println("Salary: " + salary);
        System.out.println("Conditions: " + teacherCondition);
    }
    public void printShort(){
        System.out.println(name + " " + surname + " " + yearOfBirth + " " + salary + " " + teacherCondition);
//        System.out.printf("%s\t%s\t%d\t%d\t%s\n", name, surname, yearOfBirth, salary, teacherCondition);

    }
    @Override
    public int compareTo(Teacher t) {
        int surnames = surname.compareTo(t.surname);
        if(surnames == 0){
            return name.compareTo(t.name);
        }else{
            return surnames;
        }
    }

}
