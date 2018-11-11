import java.time.LocalTime;
import java.util.Scanner;

public class Greeting {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int h = 0;
        String nome;
        System.out.print("Hora (auto)? ");
        if (kbd.hasNextInt()) {
            h = kbd.nextInt();
            kbd.nextLine();
        } else {
            kbd.nextLine();
            h = LocalTime.now().getHour();
            System.out.println("Hora = " + LocalTime.now().getHour());
        }
        if (h < 0 || h > 23) System.out.println("Horas invalidas");
        else {
            System.out.print("Nome? ");
            nome = kbd.nextLine();
            if (h >= 7 && h <= 12) System.out.println("Bom dia " + nome);
            else if (h >= 13 && h < 20) System.out.println("Boa tarde " + nome);
            else System.out.println("Boa noite " + nome);
        }
    }
}
