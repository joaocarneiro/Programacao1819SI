import java.util.Scanner;
public class Factorial {

	public static void main(String[] args) {
		Scanner kbd = new Scanner (System.in);
		System.out.print("Insira um valor: ");
		System.out.println(factorial(kbd.nextInt()));
	}
	
	public static int factorial (int n) {
       if(n==0 || n==1) return 1;
       return factorial(n-1) * n;
	}
}
