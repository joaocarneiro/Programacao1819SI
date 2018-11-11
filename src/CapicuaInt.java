import java.util.Scanner;
public class CapicuaInt {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        int capi, num=0;
        System.out.println("Insira um numero");
        capi = kbd.nextInt();
        for (int i=1;capi>0;i=i*10){
            num=num+(capi%10)*i;
        }
    }
}
