import java.time.LocalTime;
import java.util.Scanner;
public class Greeting {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        int h=0;
        String nome;
        char ch1, ch2;
        String hora;
        System.out.print("Hora (auto)? ");
        hora=kbd.nextLine();
        ch1=hora.charAt(0);

        if(hora.length()==2){
            ch2=hora.charAt(1);
            h=(ch1-'0')*10+(ch2-'0');
        }
        else if(hora.length()==1)
            h=ch1-'0';
        else {
            h = LocalTime.now().getHour();
            System.out.println("Hora = "+LocalTime.now().getHour());
        }
        if(h<0||h>23)
            System.out.println("Horas invalidas");
        else{
            System.out.print("Nome? ");
            nome = kbd.nextLine();
            if(h>=7&&h<=12) System.out.println("Bom dia " + nome);
            else if(h>=13&&h<20) System.out.println("Boa tarde " + nome);
            else System.out.println("Boa noite " + nome);
        }
    }
}
