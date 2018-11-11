import java.util.Scanner;

public class Travel {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int hp, mp, sp, hc, mc, sc, hd = 0, md = 0, sd = 0;

        System.out.print("Partida? ");
        hp = kbd.nextInt();
        if (hp < 0 || hp > 23) System.out.println("Horas invalidas");
        mp = kbd.nextInt();
        if (mp < 0 || mp > 59) System.out.println("Minutos invalidos");
        sp = kbd.nextInt();
        if (sp < 0 || sp > 59) System.out.println("Segundos invalidos");

        System.out.print("Chegada? ");
        hc = kbd.nextInt();
        if (hc < 0 || hc > 23) System.out.println("Horas invalidas");
        mc = kbd.nextInt();
        if (mc < 0 || mc > 59) System.out.println("Minutos invalidos");
        sc = kbd.nextInt();
        if (sc < 0 || sc > 59) System.out.println("Segundos invalidos");

        if (hp > hc) System.out.println("Partida > Chegada");
        else {
            if(md<0) {
                hd--;
                md = (60 - mp) + mc;
            }
            if(sd<0){
                md--;
                sd = (60 - sp) + sc;
            }
            if(md>60){
                hd++;
                md = md - 60;
            }
            System.out.println("Duracao = " + hd + ":" + md + ":" + sd);
        }

    }
}
