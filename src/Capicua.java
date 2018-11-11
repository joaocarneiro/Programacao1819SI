import java.util.Scanner;
public class Capicua {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        String capi;
        int i;
        int j;

        System.out.println("Insira uma palavra");
        capi=kbd.next();
        i=0;
        j=capi.length()-1;
        boolean capicua = true;
        while(capicua&&i<j){
            if(capi.charAt(i)!=capi.charAt(j))
                capicua=false;
            i++;
            j--;
        }
        if(capicua)
            System.out.print("Capicua");
        else
            System.out.print("Nao e' Capicua");
    }
}
