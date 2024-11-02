import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean again = false;
        Scanner sc = new Scanner(System.in);
        do{
            int _choice;
            System.out.println("1 - Square, 2 - Triangle, 3 - Circle");
            _choice = sc.nextInt();
            Figure figure = null;
            switch(_choice){
                case 1: figure = new Square(); break;
                case 2: figure = new Triangle();break;
                case 3: figure = new Circle(); break;
                default: continue;
            }

            if(figure != null){
                figure.print();
                System.out.printf("Do you want it to make 3D? [true/false]: ");
                boolean is3D = sc.nextBoolean();
                if(is3D){
                    ThreeDim figure3D = new ThreeDim(figure);
                    figure3D.print();
                }
            }
            System.out.println("Again? [true/false]:");
            again = sc.nextBoolean();
        }while(again);

    }
}