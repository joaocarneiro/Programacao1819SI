import java.util.Scanner;

public class Slope {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double x1, x2, y1, y2, m, b;

        System.out.print("(x1, y1)? ");
        x1 = in.nextDouble();
        y1 = in.nextDouble();
        System.out.print("(x2, y2)? ");
        x2 = in.nextDouble();
        y2 = in.nextDouble();

        m = (y1 - y2) / (x1 - x2);
        b = y1 - m * x1;

        if (x1 == x2) System.out.println("x = " + x1);
        else System.out.println("y = " + m + "x + " + b);
    }
}
