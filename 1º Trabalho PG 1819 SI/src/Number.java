import java.util.Scanner;

class Number {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int num, m = 0, d = 0, c = 0, u = 0;
        System.out.print("Valor (0..9999)? ");
        num = kbd.nextInt();
        m = num / 1000;
        num -= m * 1000;
        c = num / 100;
        num -= c * 100;
        d = num / 10;
        u = num % 10;
        num = m * 1000 + c * 100 + d * 10 + u;
        System.out.print(num + " = " + m + " milhar(es) + " + c + " centena(s) + " + d + " dezena(s) + " + u + " unidade(s)");
    }
}
    