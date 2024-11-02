import java.util.Scanner;

public class Circle extends Figure implements Printing{
    private double radius;

    boolean is3D;
    double H;
    public Circle() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter radius: ");
        this.radius = sc.nextDouble();
    }

    @Override
    public void print() {
        System.out.printf("Circle(r = %f)\nArea: %f\nPerimeter: %f\n", radius, calculateArea(), calculatePerimeter());
    }

    @Override
    double calculateArea() {
        return Math.PI * Math.pow(radius,2);
    }
    @Override
    double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

}
