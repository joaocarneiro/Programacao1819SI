import java.util.Scanner;
public class Distance {
    public static void main (String[]args){
        Scanner in = new Scanner(System.in);
        double x1,x2,y1,y2,res;

        System.out.println("Insert coordinates for two points: A and B");
        System.out.println("Point A");
        System.out.print("x1: ");
        x1 = in.nextDouble();
        System.out.print("x2: ");
        x2 = in.nextDouble();
        System.out.println("Point B");
        System.out.print("y1: ");
        y1 = in.nextDouble();
        System.out.print("y2: ");
        y2 = in.nextDouble();

        res = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        System.out.println("The distance between points A and B is: "+res);
    }
}
