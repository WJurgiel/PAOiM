import java.util.Scanner;
public class ThreeDim implements Printing{
    final private double h;
    final private Figure fig;

    ThreeDim(Figure fig){
        System.out.printf("Height: ");
        Scanner sc = new Scanner(System.in);
        this.h = sc.nextDouble();
        this.fig = fig;
    }
    private double calculate3DArea(){
        return 2 * fig.calculateArea() + h * fig.calculatePerimeter();
    };
    private double calculateVolume(){
        return fig.calculateArea() * h;
    }
    public void print(){
        System.out.printf("3D Area: %f, Volume: %f\n", calculate3DArea(), calculateVolume());
    }
}
