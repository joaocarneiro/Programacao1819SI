import java.util.Scanner;

public class Count4 {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int a, b, c, d, par = 0, impar = 0, iguais = 0;

        System.out.println("4 valores? ");
        a = kbd.nextInt();
        if (a % 2 == 0) par++;
        else impar++;
        b = kbd.nextInt();
        if (b % 2 == 0) par++;
        else impar++;
        c = kbd.nextInt();
        if (c % 2 == 0) par++;
        else impar++;
        d = kbd.nextInt();
        if (d % 2 == 0) par++;
        else impar++;

        if (a == b && b == c && c == d) iguais = 4;
        else if (a == b && (b == c || b == d) || c == d && (b == c || a == c)) iguais = 3;
        else if (a == b || a == c || a == d || b == c || b == d || c == d) iguais = 2;
        else iguais = 0;

        System.out.print("Iguais:" + iguais + " Pares:" + par + " Impares:" + impar);
    }
}
