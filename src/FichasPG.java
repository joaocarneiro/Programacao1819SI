import java.util.Scanner;
public class FichasPG {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
//        double f1,f2,f3,f4,med,min;
//
//        System.out.print("Fichas ? ");
//        f1=kbd.nextDouble();
//        f2=kbd.nextDouble();
//        f3=kbd.nextDouble();
//        f4=kbd.nextDouble();
//
//        min=f1;
//
////        if(f2<f1) min=f2;
////        if(f3<f2&&f3<f1) min=f3;
////        if(f4<f3&&f4<f2&&f4<f1) min=f4;
//
//        if(f2<min) min=f2;
//        if(f3<min) min=f3;
//        if(f4<min) min=f4;
//
//        med = (f1+f2+f3+f4-min)/3;
//
//        if(med<8) System.out.println("Reprovado com "+med);
//        else System.out.println("Aprovado com "+med);

        int n, i=0;
        double f=-1,big1=-1,big2=-1,big3=-1,aux=0;

        System.out.println("n? ");
        n=kbd.nextInt();
        System.out.println("Insira os valores das "+n+" fichas");
        f=kbd.nextDouble();
        big1=f;
        while(i<n-1){
            f=kbd.nextDouble();
            if(f>big1){
                big3=big2;
                big2=big1;
                big1=f;
            }
            else if(f>big2){
                big3=big2;
                big2=f;
            }
            else if(f>big3){
                big3=f;
            }
            ++i;
        }
        System.out.println("Melhor ficha: "+big1);
        System.out.println("2a Melhor ficha: "+big2);
        System.out.println("3a Melhor ficha: "+big3);
        System.out.println("Media das 3 melhores: "+((big1+big2+big3)/3));
















    }
}
