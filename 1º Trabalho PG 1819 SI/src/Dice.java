import java.util.Scanner;

class Dice {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        double num1, num2;
        System.out.print("Primeiro Dado = [");
        num1 = Math.random() * 6 + 1;
        System.out.println((int) num1 + "]");
        System.out.print("Prima Enter");
        kbd.nextLine();
        System.out.print("Segundo Dado = [");
        num2 = Math.random() * 6 + 1;
        System.out.println((int) num2 + "]");
        if ((int) num1 == (int) num2)
            System.out.print("Total a dobrar = " + ((int) num1 + (int) num2) * 2);
        else
            System.out.print("Total = " + ((int) num1 + (int) num2));
    }
}
