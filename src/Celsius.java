import java.util.Scanner;
public class Celsius {
    public static void main (String[]args){
        Scanner in = new Scanner(System.in);
        System.out.print("Insert the temperature in degrees Celsius: ");
        double cel = in.nextDouble();
        double fah = ((cel*9)/5)+32;
        System.out.print(cel+" degrees Celsius corresponds to "+fah+" degrees Fahrenheit");
    }
}
