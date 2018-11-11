import java.util.Scanner;
public class PrintUnique {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        int size = kbd.nextInt();
        int [] list = new int [size];
        int j;
        int i;
        int k;
        for(i=0;i<size;++i) {
            list[i] = kbd.nextInt();
        }
        for(j=0,k=1;j<i;++k,++j){
            if(list[k]==list[j])
                continue;

        }
        if(j==i)
            System.out.print(list[i]);
    }
}
