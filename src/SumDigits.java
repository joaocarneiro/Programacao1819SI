import java.util.Scanner;
public class SumDigits {
    public static void main (String[]args){
        Scanner in = new Scanner (System.in);
        System.out.print("Insert a number with 3 digits: ");
        int num = in.nextInt();
        int res = num%10;
        res+=(num/10)%10;
        res+=(num/10)/10;
        System.out.println("The sum of the 3 digits is: "+res);
    }
}
