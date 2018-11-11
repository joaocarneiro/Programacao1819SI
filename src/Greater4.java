import java.util.Scanner;

public class Greater4{
    public static void main (String[]args){
        double a,b,c,d,aux=0;
        Scanner kbd = new Scanner(System.in);

        System.out.println("Insira 4 numeros");
        a=kbd.nextDouble();
        b=kbd.nextDouble();
        c=kbd.nextDouble();
        d=kbd.nextDouble();

        if (a>b) { aux = a; a = b; b = aux; }
        if (c>d) { aux = c; c = d; d = aux; }
        if (a>c) { aux = a; a = c; c = aux; }
        if (b>d) { aux = b; b = d; d = aux; }
        if (b>c) { aux = b; b = c; c = aux; }

        System.out.println("O maior numero e' "+d);
        System.out.println("O segundo maior numero e' "+c);
    }
}