import java.util.Scanner;

public class GreaterThanFirst {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int num, num2, max = 0, min = 0;
        double med = 0;
        System.out.println("Insira 10 numeros");
        num = kbd.nextInt();
        num2 = 0;
        max = num;
        min = num;
        med+=num;

        //USING FOR LOOP
//        for (int i = 0; i < 10; i++) {
//            num2 = kbd.nextInt();
//            if (num2 > num) System.out.println(num2 + " e' maior");
//            if(num2>max) max=num2;
//            if(num2<min) min=num2;
//            med=med+num2;
//        }

        //USING WHILE LOOP
        int i = 1;
        while(i<10){
            num2 = kbd.nextInt();
            if (num2 > num) System.out.println(num2 + " e' maior");
            if(num2>max) max=num2;
            if(num2<min) min=num2;
            med=med+num2;
            ++i;
        }

        System.out.println();
        System.out.println(max + " e' o maior numero inserido");
        System.out.println(min + " e' o menor numero inserido");
        System.out.println(med/10 + " e' a media dos numero inseridos");
    }
}
