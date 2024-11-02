import java.util.Scanner;

public class Triangle extends Figure implements Printing{
    double a, b, c;
    double H;
    boolean is3D;
    double area;

    public Triangle() {
        Scanner sc = new Scanner(System.in);
        do{
            System.out.print("Enter correct sides of triangle:\n");
            System.out.print("Enter a: ");
            this.a = sc.nextDouble();
            System.out.print("Enter b: ");
            this.b = sc.nextDouble();
            System.out.print("Enter c: ");
            this.c = sc.nextDouble();

            if(!isTriangle()){
                System.out.println("Wrong values!");
            }
        }while(!isTriangle());


        area = calculateArea();
    }
    @Override
    double calculateArea() {
        double p = calculatePerimeter()/2;
        return Math.sqrt(p *(p-a)*(p-b)*(p-c));
    }

    @Override
    double calculatePerimeter() {
        return a + b + c;
    }

    @Override
    public void print() {
        System.out.printf("Triangle(a = %f, b = %f, c = %f)\nArea: %f\nPerimeter: %f\n", a,b,c, calculateArea(), calculatePerimeter());
    }
    boolean isTriangle(){
        return (a < b + c) && (b < a + c) && (c < a + b);
    }
}
