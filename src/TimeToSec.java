import java.util.Scanner;
public class TimeToSec {
    public static void main (String[]args){
        Scanner in = new Scanner(System.in);
        int h, m, s;
        System.out.println("Insert hours, minutes and seconds");
        System.out.print("Hours?");
        h = in.nextInt();
        if(h<0||h>23) System.out.println("Invalid number of hours!");
        else{
            System.out.print("Minutes?");
            m = in.nextInt();
            if(m<0||m>59) System.out.println("Invalid number of minutes!");
            else{
                System.out.print("Seconds?");
                s = in.nextInt();
                if(s<0||s>59) System.out.println("Invalid number of seconds!");
                else{
                    System.out.print("The number of seconds in "+h+" hour(s), "+m+" minute(s) and "+s+" second(s) is: ");
                    s+=h*3600+m*60;
                    System.out.print(s);
                }
            }
        }

    }
}
