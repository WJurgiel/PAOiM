import java.util.Scanner;

public class Square extends Figure {
    private double a, b, H;
    private double area;
    private boolean is3D;

    public Square() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a: ");
        this.a = sc.nextDouble();
        System.out.print("Enter b: ");
        this.b = sc.nextDouble();

        area = calculateArea();
    }

    @Override
    public void print() {
        System.out.printf("Square(a = %f, b = %f)\nArea: %f\nPerimeter: %f\n", a,b, area, calculatePerimeter());

    }

    @Override
    double calculateArea() {
        return a * b;
    }

    @Override
    double calculatePerimeter() {
        return 2 * a + 2 * b;
    }
}
