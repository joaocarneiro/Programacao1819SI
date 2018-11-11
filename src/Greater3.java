import java.util.Scanner;

public class Greater3 {
    public static void main(String[] args) {
        int a, b, c;
        Scanner kbd = new Scanner(System.in);
        System.out.println("Insert 3 whole numbers");
        System.out.print("a = ");
        a = kbd.nextInt();
        System.out.print("b = ");
        b = kbd.nextInt();
        System.out.print("c = ");
        c = kbd.nextInt();
        int big = a;
        if (b > big) big = b;
        if (c > big) big = c;
        System.out.println("The biggest number is " + big);
    }
}
